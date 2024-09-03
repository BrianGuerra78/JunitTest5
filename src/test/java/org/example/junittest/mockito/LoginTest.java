package org.example.junittest.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginTest {

    @InjectMocks
    private Login login;

    @Mock
    private WebService webService;

    @Captor
    private ArgumentCaptor<Callback> callbackCaptor;//capturar argumentos simulados

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doLoginTest(){
        doAnswer(new Answer<>(){
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                String user = invocationOnMock.getArgument(0);
                assertEquals("admin", user);
                String password = invocationOnMock.getArgument(1);
                assertEquals("admin", password);
                Callback callback = invocationOnMock.getArgument(2);
                callback.onSuccess("Login Success");
                return null;
            }
        }).when(webService).login(anyString(),anyString(), any(Callback.class));

        login.doLogin();
        verify(webService, times(1)).login(anyString(), anyString(), any(Callback.class));
        assertEquals(true, login.isLogin);
    }

    @Test
    public void doLoginErrorTest(){
        doAnswer(new Answer<>(){
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                String user = invocationOnMock.getArgument(0);
                assertEquals("admin", user);
                String password = invocationOnMock.getArgument(1);
                assertEquals("admin", password);
                Callback callback = invocationOnMock.getArgument(2);
                callback.onFail("Login Fail");
                return null;
            }
        }).when(webService).login(anyString(),anyString(), any(Callback.class));

        login.doLogin();
        verify(webService, times(1)).login(anyString(), anyString(), any(Callback.class));
        assertEquals(false, login.isLogin);
    }

    @Test
    void doLoginCaptorTest(){
        login.doLogin();
        verify(webService, times(1)).login(anyString(), anyString(), callbackCaptor.capture());
        assertEquals(false, login.isLogin);
        callbackCaptor.getValue().onSuccess("Login Success");
        assertEquals(true, login.isLogin);
        callbackCaptor.getValue().onFail("Login Fail");
        assertEquals(false, login.isLogin);
    }
}