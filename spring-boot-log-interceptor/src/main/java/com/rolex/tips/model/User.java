/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
@Setter
@Getter
public class User {

    Long id;
    String name;
    Integer age;

}
