package com.rolex.tips.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rolex.tips.event.InstEvent;
import com.rolex.tips.mapper.JobInstMapper;
import com.rolex.tips.model.EventModel;
import com.rolex.tips.model.JobInst;
import com.rolex.tips.service.JobInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@Slf4j
public class JobInstServiceImpl implements JobInstService {

    @Autowired
    JobInstMapper jobInstMapper;

    @Override
    public void save(JobInst jobInst) {
        jobInstMapper.insert(jobInst);
    }

    @Override
    public JobInst findById(Long jobId) {
        return jobInstMapper.selectById(jobId);
    }

    @Override
    public List<JobInst> findAll() {
        return jobInstMapper.selectAll();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void process(EventModel eventModel) throws Exception {
        JobInst jobInst = new JobInst();
        jobInst.setJobName(eventModel.getEventName());
        jobInstMapper.insert(jobInst);
        Thread.sleep(3000);
        UpdateWrapper<JobInst> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("job_name", jobInst.getJobName());
        jobInst = new JobInst();
        jobInst.setJobName(eventModel.getEventName() + "_1");
        jobInstMapper.update(jobInst, updateWrapper);
        int i = 1 / 0;
    }

    @Override
    public void asyncProcess(EventModel eventModel) throws Exception {
        log.info("--------asyncProcess--------");
        JobInst jobInst = new JobInst();
        jobInst.setJobName(eventModel.getEventName());
        jobInstMapper.insert(jobInst);
        Thread.sleep(3000);
        UpdateWrapper<JobInst> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("job_name", jobInst.getJobName());
        jobInst = new JobInst();
        jobInst.setJobName(eventModel.getEventName() + "_1");
        jobInstMapper.update(jobInst, updateWrapper);
        int i = 1 / 0;
        log.info("--------asyncProcess--------");
    }

    @Transactional(rollbackFor = Exception.class)
    @TransactionalEventListener(fallbackExecution = true)
    public void asyncProcessEvent(InstEvent instEvent) throws Exception {
        log.info("--------asyncProcessEvent--------");
        JobInst jobInst = new JobInst();
        jobInst.setJobName(instEvent.getEventModel().getEventName());
        jobInstMapper.insert(jobInst);
        Thread.sleep(3000);
        UpdateWrapper<JobInst> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("job_name", jobInst.getJobName());
        jobInst = new JobInst();
        jobInst.setJobName(instEvent.getEventModel().getEventName() + "_1");
        jobInstMapper.update(jobInst, updateWrapper);
        int i = 1 / 0;
        log.info("--------asyncProcessEvent--------");
    }

}
