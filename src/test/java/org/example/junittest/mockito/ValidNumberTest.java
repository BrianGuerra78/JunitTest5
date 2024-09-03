package org.example.junittest.mockito;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidNumberTest {

    private ValidNumber validNumber;

    @BeforeEach
    void setUp(){
        validNumber = new ValidNumber();
        System.out.println("@BeforeEach -> setUp()");
    }

    @AfterEach
    void tearDown(){
        validNumber = null;
        System.out.println("@AfterEach -> tearDown()");
    }

    @Test
    void checkTest(){
        assertEquals(true, validNumber.check(5));
    }

    @Test
    void checkNegativeTest(){
        assertEquals(false, validNumber.check(-5));
    }

    @Test
    void checkStringTest(){
        assertEquals(false, validNumber.check("5"));
    }

    @Test
    void checkZeroTest(){
        assertEquals(true, validNumber.checkZero(-57));
    }

    @Test
    void checkZeroStringTest(){
        assertEquals(false, validNumber.checkZero("5"));
    }

    @Test
    void checkZero0Test(){
        assertThrows(ArithmeticException.class, () -> {
            validNumber.checkZero(0);
        });
    }

    @Test
    void doubleToIntTest(){
        assertEquals(9, validNumber.doubleToInt(9.99));
    }

    @Test
    void doubleToIntErrorTest(){
        assertEquals(0, validNumber.doubleToInt("9.99"));
    }
}