/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author rolex
 * @since 2021
 */
@Data
@AllArgsConstructor
public class Response {

    private int code;
    private String msg;
    private Object result;

    public static Response success(Object result) {
        return new Response(200, "success", result);
    }

    public static Response success() {
        return new Response(200, "success", null);
    }

    public static Response failure(int code, String msg) {
        return new Response(code, msg, null);
    }

}
