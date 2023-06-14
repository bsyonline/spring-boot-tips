package com.rolex.tips.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rolex.tips.model.EventModel;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface EventModelMapper extends BaseMapper<EventModel> {
    List<EventModel> selectAll();
}
