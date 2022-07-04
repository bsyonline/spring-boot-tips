package com.rolex.tips.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
public class Role implements Serializable {

    private Integer id;
    private String name;
    private List<User> users;

}
