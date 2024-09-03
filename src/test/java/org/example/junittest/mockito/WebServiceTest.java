package org.example.junittest.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class WebServiceTest {

    private WebService webService;

    @Mock
    private Callback callback;

    @BeforeEach
    void setUp(){
        webService = new WebService();
        MockitoAnnotations.initMocks(this);
        System.out.println("@BeforeEach -> setUp()");
    }

    @Test
    void checkLoginTest(){
        assertTrue( webService.checkLogin("admin", "admin"));
    }

    @Test
    void checkLoginErrorTest(){
        assertFalse( webService.checkLogin("admin1", "admin1"));
    }

    @Test
    void loginTest(){
        webService.login("admin", "admin", callback);
        verify(callback).onSuccess("Login Success");
    }

    @Test
    void loginErrorTest(){
        webService.login("admin1", "admin1", callback);
        verify(callback).onFail("Login Fail");
    }
}