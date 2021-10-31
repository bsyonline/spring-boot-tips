package com.rolex.tips.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rolex
 * @since 2020
 */
@Target(value= ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
}
