package com.rolex.tips.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rolex.tips.model.EventModel;
import com.rolex.tips.model.JobInst;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface JobInstMapper extends BaseMapper<JobInst> {
    List<JobInst> selectAll();
}
