package com.example.tag.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BaseException extends RuntimeException {

    private String code;
    private String msg;

    public BaseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
