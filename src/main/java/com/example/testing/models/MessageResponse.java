package com.example.testing.models;

public class MessageResponse {
    public int getCode() {
        return code;
    }

    public MessageResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    int code;
    String message;


}
