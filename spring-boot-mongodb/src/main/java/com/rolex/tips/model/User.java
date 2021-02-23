package com.rolex.tips.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
@Document(collection = "user")
public class User {
    @Id
    Long id;
    String name;
    Integer age;
    Integer gender;
    LocalDate birth;
}
