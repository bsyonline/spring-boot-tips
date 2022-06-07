package com.rolex.tips.tx.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
@TableName("t_user")
@ToString
public class User extends Model<User> {
    long id;
    String name;
    int age;
    int gender;
    String skill;
    Date createTime;
    Date updateTime;
}
