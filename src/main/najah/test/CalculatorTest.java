package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import main.najah.code.Calculator;

@DisplayName("Calculator Tests")
@TestMethodOrder(OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public class CalculatorTest {

    Calculator calc;

    @BeforeAll
    static void BeforeAll() throws Exception {
        System.out.println("Start Calculator tests");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("All Calculator tests have completed");
    }

    @BeforeEach
    void setup() {
        calc = new Calculator();
    }
    
    @Test
    @Order(1)
    @DisplayName("Divide Number")
    void testDivideNumber() {
        assertEquals(5, calc.divide(10, 2));
    }
    

    @Test
    @Order(2)
    @DisplayName("Divide By Zero")
    void testDivideByZero() {
        ArithmeticException ex = assertThrows(ArithmeticException.class, () -> calc.divide(2, 0));
        assertEquals("Cannot divide by zero", ex.getMessage());
    }


    @Test
    @Order(3)
    @DisplayName("valid add number")
    void testAddValid() {
        assertEquals(6, calc.add(1, 2, 3));    
    }
    
    @Test
    @Order(4)
    @DisplayName("Factory valid")
    void testFactory() {
        assertEquals(1, calc.factorial(0), "Factorial of 0 should be 1");
        assertEquals(1, calc.factorial(1), "Factorial of 1 should be 1");
        assertEquals(120, calc.factorial(5), "Factorial of 5 should be 120");
    }


    @Test
    @Order(5)
    @DisplayName("Negateve number")
    void testFactorialNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> calc.factorial(-3));
        assertEquals("Negative input", ex.getMessage());
    }

    
    @Test
    @Order(6)
    @DisplayName("Add Empty array")
    void testAdditionEmptyArray(){
        assertEquals(0, calc.add());
    }

 

  

  
}
