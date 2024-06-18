package org.rmj.guanzongroup.gsecurity.data.remote.response.base;

import org.rmj.guanzongroup.gsecurity.data.remote.response.error.ErrorResponse;

public class BaseResponse<T> {

    // Receives the result from JSON...
    private String result;
    private String message;

    // If result = error, get the error to get the message...
    private ErrorResponse error;

    // Generic data type to handle response from API which consist of JSON with key 'data'
    private T data;

    public BaseResponse() { }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public ErrorResponse getError() { return error; }

    public T getData() { return data; }
}
