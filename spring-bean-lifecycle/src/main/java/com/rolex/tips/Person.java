/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2020
 */
@Component
public class Person {
    @Value("${name:Tom}")
    String name;
}
