package com.rolex.tips.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
@TableName("t_job_inst")
public class JobInst extends Model<JobInst> {
    @TableId(value = "job_id", type = IdType.AUTO)
    long jobId;
    String jobName;
}
