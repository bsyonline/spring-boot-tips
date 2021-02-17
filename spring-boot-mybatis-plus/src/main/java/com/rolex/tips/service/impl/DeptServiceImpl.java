package com.rolex.tips.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rolex.tips.entity.Dept;
import com.rolex.tips.mapper.DeptMapper;
import com.rolex.tips.service.IDeptService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rolex
 * @since 2020-06-20
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
