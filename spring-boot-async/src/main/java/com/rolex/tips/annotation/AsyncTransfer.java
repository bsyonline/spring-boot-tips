package com.rolex.tips.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsyncTransfer {
    String condition();

    String asyncMethod();
}
