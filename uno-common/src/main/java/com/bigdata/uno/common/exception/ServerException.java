package com.bigdata.uno.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServerException extends RuntimeException {
    private int code;
    public ServerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServerException(String message) {
        super(message);
    }
}
