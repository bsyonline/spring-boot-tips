package com.rolex.tips.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    int code;
    String msg;
    T result;

    public static <T> Response success(T result) {
        return new Response(200, "success", result);
    }
}
