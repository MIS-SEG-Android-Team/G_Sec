package org.rmj.guanzongroup.gsecurity.data.remote.response.branch;

import org.rmj.guanzongroup.gsecurity.data.remote.response.error.ErrorResponse;

public class BranchResponse<T> {


    // Receives the result from JSON...
    private String result;

    // If result = error, get the error to get the message...
    private ErrorResponse error;

    // Generic data type to handle response from API which consist of JSON with key 'detail'
    private T detail;

    public BranchResponse() {

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

    public T getDetail() {
        return detail;
    }
}
