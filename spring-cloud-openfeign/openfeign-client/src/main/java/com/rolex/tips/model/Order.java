/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rolex
 * @since 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    String id;
    String date;
    String type;

}
