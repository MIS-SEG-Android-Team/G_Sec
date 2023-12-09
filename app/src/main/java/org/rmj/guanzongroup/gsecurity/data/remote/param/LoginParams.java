package org.rmj.guanzongroup.gsecurity.data.remote.param;

public class LoginParams {


    // User Email must be the input for this area variable
    private String user;

    // User password must be the input for this area variable
    private String pswd;

    /**
     * Parameter structure upon ApiService...
     * {
     *   "user": "sampleEmail.domaim.com",
     *   "pswd": "password"
     * }
     */
    public LoginParams() {
    }

    public String getUsername() {
        return user;
    }

    public void setUsername(String username) {
        this.user = username;
    }

    public String getPassword() {
        return pswd;
    }

    public void setPassword(String password) {
        this.pswd = password;
    }
}
