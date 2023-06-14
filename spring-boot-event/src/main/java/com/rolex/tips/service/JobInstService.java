package com.rolex.tips.service;

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
public interface JobInstService {
    void save(JobInst jobInst);

    JobInst findById(Long jobId);

    List<JobInst> findAll();

    void process(EventModel eventModel) throws InterruptedException, Exception;

    void asyncProcess(EventModel eventModel) throws InterruptedException, Exception;
}
