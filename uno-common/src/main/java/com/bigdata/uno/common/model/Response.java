package com.bigdata.uno.common.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Integer status;
    protected String msg;
    protected Object data;

    public static Response success(Object data) {
        return Response.builder().status(0).data(data).build();
    }

    public static Response fail(int code, String msg) {
        return Response.builder().status(code).msg(msg).build();
    }
}
