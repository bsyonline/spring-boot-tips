package com.rolex.tips.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

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
public class User implements Serializable {
    Long id;
    String name;
}
