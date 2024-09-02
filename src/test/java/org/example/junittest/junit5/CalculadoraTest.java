package org.example.junittest.junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraTest {

    private Calculadora cal;

    private Calculadora calNull;

    private static Calculadora calStatic;

    @BeforeAll//solo se ejecuta una vez(ejmplo una base de datos)
    static void beforeAll(){
        calStatic = new Calculadora();
        System.out.println("@BeforeAll -> beforeAll()");
    }

    @BeforeEach//se ejecuta siempre al principio
    void setUp(){
        cal = new Calculadora();
        System.out.println("@BeforeEach -> setUp()");
    }

    @AfterEach//sirve para liberar recursos
    void tearDown(){
        cal = null;
        System.out.println("@AfterEach -> tearDown()");
    }

    @AfterAll//es el ultimo en ejecutarse
    static void afterAll(){
        calStatic = null;
        System.out.println("@AfterAll -> afterAll()");
    }

    @Test
    void calculadoraNotNullTest(){
        assertNotNull(calStatic);
        assertNotNull(cal, "Calculadora debe ser not null");
        System.out.println("@Test -> calculadoraNotNullTest()");
    }

    @Test
    void calculadoraNullTest(){
        assertNull(calNull);
        System.out.println("@Test -> calculadoraNullTest()");
    }

    @Test
    void addAssertTest(){
        //SetUp
        Calculadora calAssert = new Calculadora();
        int resultadoEsp = 30;
        int resultActual;
        //Action
        resultActual = calAssert.add(10,20);
        //Assert
        assertEquals(resultadoEsp, resultActual);
        System.out.println("@Test -> addAssertTest()");
    }

    @Test
    void addTest(){
        assertEquals(30, cal.add(10,20));
        System.out.println("@Test -> addTest()");
    }

    @Test
    void assertTypesTest(){
        assertTrue(1== 1);//verdadero
        assertNull(calNull);//nullo
        assertNotNull(cal);//no nulo

        Calculadora cal1 = new Calculadora();
        Calculadora cal2 = new Calculadora();
        Calculadora cal3 = cal1;

        assertSame(cal1,cal3);//mismo objeto en memoria
        assertNotSame(cal1,cal2);// no es el mismo objeto en memoria

        assertEquals("alberto", "alberto");//son iguales
        assertEquals(1, 1.4, 0.5);//tiene rango de tolerancia por los decimales
    }

    @Test
    void addValidInput_ValidExpectedTest(){
        assertEquals(11, cal.add(7,4));
    }

    @Test
    void subtractValidInput_ValidExpectedTest(){
        assertEquals(11, cal.subtract(15,4));
    }

    @Test
    void subtractValidInput_ValidNegativeResultExpectedTest(){
        assertEquals(-10, cal.subtract(10,20));
    }

    @Test
    void divideValidInput_ValidExpectedTest(){
        assertEquals(2, cal.divide(10,5));
    }

    @Test
    void divide_InValidInputTest(){
        fail("Fallo detectado manualmente - No se puede dividir por cero");
        assertEquals(2, cal.divide(15,0));
    }

    @Test
    void divide_InValidInput_ExpectedExceptionTest(){//cacha los mensajes de error
        assertThrows(ArithmeticException.class,
                () -> cal.divideByZero(2,0), "No se puede dividir por cero");
    }

    @Disabled("Disabled until bug 23 be resolved")//desahabilita test
    @Test
    void divideInvalidInputTest(){
        assertEquals(2, cal.divide(5,0));
    }

    @Test
    @DisplayName("Metodo dividir -> funciona")
    void divideValidInputNameTest(){
        assertEquals(2, cal.divide(10,5));
    }

    @Test
    void add_assertAllTest(){// recorre todos los assert aunque alguno falle
        assertAll(
                ()-> assertEquals(31, cal.add(11,20)),
                ()-> assertEquals(20, cal.add(10,20)),
                ()-> assertEquals(2,cal.add(1,1))
                );
    }

    @Nested//anida test
    class addTest{
        @Test
        void addPositiveTest(){
            assertEquals(30, cal.add(15,15));
        }
        @Test
        void addNegativeTest(){
            assertEquals(-30, cal.add(-15,-15));
        }
        @Test
        void addZeroTest(){
            assertEquals(0, cal.add(0,0));
        }
    }

    //pruebas parametrizadas
    @ParameterizedTest(name = "{index} => a={0}, b={1}, sum={2}")
    @MethodSource("addProviderData")//de donde se obtienen los datos
    void addParameterizedTest(int a, int b, int sum){
        assertEquals(sum, cal.add(a,b));
    }

    private static Stream<Arguments> addProviderData(){
        return Stream.of(
                Arguments.of(6,2,8),
                Arguments.of(-6,-2,-8),
                Arguments.of(6,-2,4),
                Arguments.of(-6,2,-4),
                Arguments.of(6,0,6)
        );
    }

    @Test
    void timeOutTest(){
        assertTimeout(Duration.ofMillis(2000), ()->{
            cal.longTaskOperation();
        });
    }
}