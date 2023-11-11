package org.rmj.guanzongroup.gsecurity.pojo.login;

public class LoginCredentials {

    private String email = "";
    private String password = "";

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static class Validator{

        private String message = "";

        public boolean isDataValid(LoginCredentials loginCredentials){
            if(loginCredentials.getEmail().trim().isEmpty()){
                message = "Please enter your email.";
                return false;

            } else if(loginCredentials.getPassword().isEmpty()){
                message = "Please enter your password.";
                return false;
            }

            return true;
        }

        public String getMessage() {
            return message;
        }
    }
}
