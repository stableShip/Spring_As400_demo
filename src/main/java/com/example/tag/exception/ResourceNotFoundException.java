package com.example.tag.exception;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String msg) {
        super(msg);
        this.setCode("1000");
    }
}
