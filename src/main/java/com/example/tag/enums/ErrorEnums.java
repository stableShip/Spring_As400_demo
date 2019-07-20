package com.example.tag.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnums {


    SUCCESS(200, "success");


    /** 错误码 */
    private final int code;
    /** 描述 */
    private final String msg;

}
