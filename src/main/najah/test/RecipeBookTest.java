package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;
import main.najah.code.Recipe;

class RecipeBookTest {

    RecipeBook book;
    Recipe base;

    @BeforeAll
    static void initSuite() {
        System.out.println("Starting RecipeBook Test Suite...");
    }

    @BeforeEach
    void setup() {
        book = new RecipeBook();
        base = generateRecipe("tea", "20", "1", "1", "1", "0");
    }

    @AfterEach
    void cleanup(TestInfo info) {
        System.out.println("Finished test: " + info.getDisplayName());
    }

    private static Recipe generateRecipe(String name, String price, String coffee, String milk, String sugar, String chocolate) {
        Recipe r = new Recipe();
        try {
            r.setName(name);
            r.setPrice(price);
            r.setAmtCoffee(coffee);
            r.setAmtMilk(milk);
            r.setAmtSugar(sugar);
            r.setAmtChocolate(chocolate);
        } catch (RecipeException e) {
            fail("Failed to create recipe: " + e.getMessage());
        }
        return r;
    }

    @Test
    @DisplayName("Add simple recipe")
    void testAddRecipe() {
        assertTrue(book.addRecipe(base));
        assertEquals("tea", book.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Don't allow duplicate recipes")
    void testAddDuplicate() {
        book.addRecipe(base);
        Recipe duplicate = generateRecipe("tea", "20", "1", "1", "1", "0");
        assertFalse(book.addRecipe(duplicate));
    }

    @Test
    @DisplayName("Delete recipe at index 0")
    void testDelete() {
        book.addRecipe(base);
        String removed = book.deleteRecipe(0);
        assertEquals("tea", removed);
    }

    @Test
    @DisplayName("Delete from empty book")
    void testDeleteFromEmpty() {
        assertNull(book.deleteRecipe(0));
    }

    @Test
    @DisplayName("Delete with wrong index")
    void testDeleteInvalidIndex() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> book.deleteRecipe(10));
    }

    @Test
    @DisplayName("Edit existing recipe")
    void testEdit() {
        book.addRecipe(base);
        Recipe newOne = generateRecipe("milk", "25", "0", "2", "1", "0");
        String oldName = book.editRecipe(0, newOne);
        assertEquals("tea", oldName);
    }

    @Test
    @DisplayName("Edit recipe at empty slot")
    void testEditEmptySlot() {
        Recipe ghost = generateRecipe("lemon", "30", "0", "1", "1", "0");
        assertNull(book.editRecipe(0, ghost));
    }

    @ParameterizedTest
    @MethodSource("simpleRecipeProvider")
    @DisplayName("Add several simple recipes")
    void testAddMany(Recipe recipe) {
        assertTrue(book.addRecipe(recipe));
    }

    static Stream<Recipe> simpleRecipeProvider() {
        return Stream.of(
            generateRecipe("coffee", "35", "3", "1", "1", "0"),
            generateRecipe("milk", "25", "0", "3", "1", "0"),
            generateRecipe("choco", "40", "1", "1", "2", "3"),
            generateRecipe("lemon", "15", "0", "0", "2", "0")
        );
    }
}
