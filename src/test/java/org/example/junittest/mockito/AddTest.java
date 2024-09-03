package org.example.junittest.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AddTest {
    @InjectMocks
    private Add add;

    @Mock
    private ValidNumber validNumber;

    @Mock
    private Print print;

    @Captor
    private ArgumentCaptor<Integer> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTest(){
        when(validNumber.check(3)).thenReturn(false);
        boolean checkNumber = validNumber.check(3);
        assertEquals(false, checkNumber);
    }

    @Test
    void add2Test(){
        when(validNumber.check("a")).thenReturn(false);
        boolean checkNumber = validNumber.check("a");
        assertEquals(false, checkNumber);
    }

    @Test
    void addMockExceptionTest(){
        when(validNumber.checkZero(0)).thenThrow(new ArithmeticException("No se puede dividir por cero"));
        Exception exception = null;
        try {
            validNumber.checkZero(0);
        }catch (ArithmeticException e){
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void addRealMethodTest(){
        when(validNumber.check(3)).thenCallRealMethod();
        assertEquals(true, validNumber.check(3));

        when(validNumber.check("3")).thenCallRealMethod();
        assertEquals(false, validNumber.check("3"));
    }

    @Test
    void addDoubleToIntThenAnswerTest(){
        Answer<Integer> answer = new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                return 7;
            }
        };

        when(validNumber.doubleToInt(7.7)).thenAnswer(answer);
        assertEquals(14, add.addInt(7.7));
    }

    @Test
    void patterTest(){
        //Arrange
        when(validNumber.check(4)).thenReturn(true);
        when(validNumber.check(5)).thenReturn(true);
        //Act
        int result = add.add(4, 5);
        //Assert
        assertEquals(9, result);
    }

    @Test
    void patter2Test(){
        //Given
        given(validNumber.check(4)).willReturn(true);
        given(validNumber.check(5)).willReturn(true);
        //Act
        int result = add.add(4, 5);
        //Assert
        assertEquals(9, result);
    }

    @Test
    void argumentMatcherTest(){
        //Given
        given(validNumber.check(anyInt())).willReturn(true);
        //Act
        int result = add.add(4, 5);
        //Assert
        assertEquals(9, result);
    }

    @Test
    void addPrintTest(){
        //Given
        given(validNumber.check(4)).willReturn(true);
        //when
        add.addPrint(4, 4);
        //then
        verify(validNumber, times(2)).check(4);
    }

    @Test
    void addPrint2Test(){
        //Given
        given(validNumber.check(4)).willReturn(true);
        given(validNumber.check(5)).willReturn(true);
        //when
        add.addPrint(4, 5);
        //then
        verify(validNumber, never()).check(99);
        verify(validNumber, atLeast(1)).check(4);
        verify(validNumber, atMost(3)).check(4);

        verify(print).showMessage(9);
        verify(print, never()).showError();
    }

    @Test
    void captorTest(){
        //Given
        given(validNumber.check(4)).willReturn(true);
        given(validNumber.check(5)).willReturn(true);
        //when
        add.addPrint(4, 5);
        //then
        verify(print).showMessage(captor.capture());
        assertEquals(9, captor.getValue());
    }

    @Spy
    List<String> spyList = new ArrayList<>();

    @Mock
    List<String> mockList = new ArrayList<>();

    @Test
    void spyTest(){
        //when
        spyList.add("one");
        spyList.add("two");
        //then
        verify(spyList).add("one");
        verify(spyList).add("two");

        assertEquals(2, spyList.size());
    }

    @Test
    void mockTest(){//devuelve el valor real
        //when
        mockList.add("one");
        mockList.add("two");
        //then
        verify(mockList).add("one");
        verify(mockList).add("two");

        assertEquals(0, mockList.size());
    }
}