package org.example.junittest.mockito;

public class Login {

    public WebService webService;

    public boolean isLogin;

    public Login(WebService webService){
        this.webService = webService;
        isLogin = false;
    }

    public void doLogin(){
        webService.login("admin", "admin", new Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                isLogin = true;
            }

            @Override
            public void onFail(String error) {
                System.out.println(error);
                isLogin = false;
            }
        });
    }
}
