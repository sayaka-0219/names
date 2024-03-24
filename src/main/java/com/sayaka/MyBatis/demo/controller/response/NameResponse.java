package com.sayaka.MyBatis.demo.controller.response;

public class NameResponse {
    private String message;

    public NameResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
