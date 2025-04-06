package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import main.najah.code.Product;

public class ProductTest {
    Product p;

    @BeforeAll
    static void BeforeAll() {
        System.out.print("Before All Test");
    }

    @BeforeEach
    void setUp() throws Exception {
        p = new Product("hedphone", 1000);
    }

    @Test
    @Order(3)
    @DisplayName("Create Product with the right data")
    void testValidProductPrice() {
        assertEquals("hedphone", p.getName());
        assertEquals(1000, p.getFinalPrice());
        assertEquals(0, p.getDiscount());
        assertEquals(1000, p.getFinalPrice());
    }

    @Test
    @Order(5)
    @DisplayName("Not negative product price")
    void testInvalidProductPrice() {
        assertEquals((assertThrows(IllegalArgumentException.class, () -> new Product("Phone", -500))).getMessage(), "Price must be non-negative");
    }

    @Test
    @Order(7)
    @DisplayName("Invalid Discount")
    void testInvalidDiscount() {
        assertEquals((assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(-20.0))).getMessage(), "Invalid discount");
    }

    @Test
    @Order(2)
    @DisplayName("Valid Discount")
    void testValidDiscount() {
        p.applyDiscount(10);
        assertEquals(p.getDiscount(), 10);
    }

    @DisplayName("true value Product after discount")
    @ParameterizedTest
    @Order(4)
    @CsvSource({
        "20.0, 800.0",
        "0.0, 1000.0",
        "50.0, 500.0"
        
    })
    void testValueProductAfterDiscount(String discountPercentageStr, String expectedPriceStr) {
        double discountPercentage = Double.parseDouble(discountPercentageStr);
        double expectedPrice = Double.parseDouble(expectedPriceStr);

        p.applyDiscount(discountPercentage);
        assertEquals(expectedPrice, p.getFinalPrice());
    }


    @Test
    @Order(6)
    @DisplayName("test applying discount with timeout")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testDiscountWithinTime() {
        p.applyDiscount(20);
        assertEquals(20, p.getDiscount());
    }

    @Test
    @Order(1)
    @DisplayName("test getters")
    void testGetters() {
        assertEquals("hedphone", p.getName());
        assertEquals(1000.0, p.getPrice());
        assertEquals(0.0, p.getDiscount());
    }

    @AfterEach
    void AfterEach() {
        System.out.println("Test has done");
    }

    @AfterAll
    static void afterAll() {
        System.out.print("After All Test");
    }
}
