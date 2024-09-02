package org.example.junittest.junit5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureCalculatorTest {

    private TemperatureCalculator tempCal;

    @BeforeEach//se ejecuta siempre al principio
    void setUp(){
        tempCal = new TemperatureCalculator();
        System.out.println("@BeforeEach -> setUp()");
    }

    @AfterEach//sirve para liberar recursos
    void tearDown(){
        tempCal = null;
        System.out.println("@AfterEach -> tearDown()");
    }

    @Test
    void toFahrenheitTest(){
        assertEquals(-9.4, tempCal.toFahrenheit(-23), 0.01);
    }
}