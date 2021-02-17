package com.rolex.tips.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author rolex
 * @since 2020-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_employee")
public class Employee extends Model<Employee> {

    private static final long serialVersionUID=1L;
    private Long id;

    private String name;

    private Integer age;

    private Long deptId;

    private String email;

    private LocalDateTime createdTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
