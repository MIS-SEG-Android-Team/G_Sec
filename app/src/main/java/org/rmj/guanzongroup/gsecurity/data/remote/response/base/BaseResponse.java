package org.rmj.guanzongroup.gsecurity.data.remote.response.base;

public class BaseResponse<T> {

    // Receives the result from JSON...
    private String result;

    // If result = error, get the error to get the message...
    private ErrorResponse error;

    // Generic data type to handle response from API which consist of JSON with key 'data'
    private T data;

    public BaseResponse() {

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ErrorResponse getError() {
        return error;
    }

    public T getData() {
        return data;
    }
}
