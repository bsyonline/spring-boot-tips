package com.rolex.tips.exception;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class AsyncTimeoutException extends RuntimeException {
    public AsyncTimeoutException(String message) {
        super(message);
    }
}
