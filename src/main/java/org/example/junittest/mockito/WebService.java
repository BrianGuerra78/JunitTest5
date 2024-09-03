package org.example.junittest.mockito;

public class WebService {

    public void login(String user, String password, Callback callback){
        if(checkLogin(user, password)){
            callback.onSuccess("Login Success");
        }else{
            callback.onFail("Login Fail");
        }
    }

    public boolean checkLogin(String userName, String password){
        if(userName.equals("admin") && password.equals("admin")){
            return true;
        }
        return false;
    }
}
