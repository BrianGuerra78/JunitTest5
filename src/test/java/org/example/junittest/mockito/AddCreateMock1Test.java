package org.example.junittest.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AddCreateMock1Test {

    private Add add;

    private ValidNumber validNumber;

    @BeforeEach
    void setUp() {
        validNumber = Mockito.mock(ValidNumber.class);//simulacion del metodo real
        add = new Add(validNumber);
    }

    @Test
    void addTest(){
        add.add(3,2);
        Mockito.verify(validNumber).check(3);
    }
}