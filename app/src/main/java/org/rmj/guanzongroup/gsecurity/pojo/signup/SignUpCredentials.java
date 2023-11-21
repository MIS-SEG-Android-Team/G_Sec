package org.rmj.guanzongroup.gsecurity.pojo.signup;

public class SignUpCredentials {

    private String sFirstNme = "";
    private String sLastName = "";
    private String sMiddName = "";
    private String sEmailAdd = "";
    private String sPassword = "";
    private String sConfirmP = "";

    public SignUpCredentials() {

    }

    public String getUserName() {
        String lsUserName = sLastName + ", " + sFirstNme;
        if(!sMiddName.trim().isEmpty()) {
            lsUserName = lsUserName + " " + sMiddName;
        }
        return lsUserName;
    }

    public String getConfirmP() {
        return sConfirmP;
    }

    public void setConfirmP(String sConfirmP) {
        this.sConfirmP = sConfirmP;
    }

    public String getFirstNme() {
        return sFirstNme;
    }

    public String getLastName() {
        return sLastName;
    }

    public String getMiddName() {
        return sMiddName;
    }

    public void setFirstNme(String sFirstNme) {
        this.sFirstNme = sFirstNme;
    }

    public void setLastName(String sLastName) {
        this.sLastName = sLastName;
    }

    public void setMiddName(String sMiddName) {
        this.sMiddName = sMiddName;
    }

    public String getEmailAdd() {
        return sEmailAdd;
    }

    public void setEmailAdd(String sEmailAdd) {
        this.sEmailAdd = sEmailAdd;
    }

    public String getPassword() {
        return sPassword;
    }

    public void setPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public static class Validator {

        private String message = "";

        public boolean isDataValid(SignUpCredentials credentials){
            if(credentials.getLastName().trim().isEmpty()) {
                message = "Please enter last name.";
                return false;
            } else if(credentials.getFirstNme().trim().isEmpty()) {
                message = "Please enter first name.";
                return false;
            } else if(credentials.getEmailAdd().isEmpty()) {
                message = "Please enter your email.";
                return false;
            } else if(credentials.getPassword().isEmpty()) {
                message = "Please enter your password.";
                return false;
            } else if(credentials.getConfirmP().isEmpty()) {
                message = "Please re-type your password to confirm.";
                return false;
            } else if(credentials.getConfirmP().equalsIgnoreCase(credentials.getPassword())) {
                message = "Password does not match";
                return false;
            } else if(credentials.getPassword().length() <= 7) {
                message = "Password must be 8 characters.";
                return false;
            }

            return true;
        }

        public String getMessage() {
            return message;
        }
    }
}
