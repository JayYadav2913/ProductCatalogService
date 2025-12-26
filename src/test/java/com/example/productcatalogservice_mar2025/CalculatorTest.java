package com.example.productcatalogservice_mar2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    public void testAdd_On2Integers_Success() {

        //Arrange
        Calculator calc = new Calculator();

        //Act
        int result = calc.add(1, 2);

        //Assert
        assert(result == 3);
    }
    @Test
    public void testDivideByZero_ResultsInArithmeticException() {
        //Arrage
        Calculator calc = new Calculator();

        //Act
        assertThrows(ArithmeticException.class,
                ()->calc.divide(1,0));


    }
}