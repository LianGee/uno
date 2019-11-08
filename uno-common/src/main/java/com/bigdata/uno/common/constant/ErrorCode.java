package com.bigdata.uno.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS(0, "成功"),

    SYSTEM_EXCEPTION(500, "系统异常"),
    CHECK_EXCEPTION(1001, "参数校验错误"),
    UNIQUE_EXCEPTION(1002, "值重复")
    ;

    private int code;
    private String desc;

}
