package com.rolex.tips.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rolex.tips.entity.Gender;
import com.rolex.tips.entity.Skill;
import lombok.Data;

import java.util.Date;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
public class UserVO {
    private Integer id;
    private String name;
    private Integer age;
    private Gender gender;
    private Skill skill;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date end;
}
