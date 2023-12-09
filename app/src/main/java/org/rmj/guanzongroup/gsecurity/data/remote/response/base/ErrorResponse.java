package org.rmj.guanzongroup.gsecurity.data.remote.response.base;

public class ErrorResponse {

    private String code;
    private String message;

    public ErrorResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
