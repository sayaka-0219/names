package com.sayaka.MyBatis.demo.exception.exceptionclass;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}

