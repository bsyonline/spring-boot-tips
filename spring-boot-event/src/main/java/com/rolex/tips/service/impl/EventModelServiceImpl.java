package com.rolex.tips.service.impl;

import com.rolex.tips.event.InstEvent;
import com.rolex.tips.mapper.EventModelMapper;
import com.rolex.tips.model.EventModel;
import com.rolex.tips.service.EventModelService;
import com.rolex.tips.service.JobInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
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
public class EventModelServiceImpl implements EventModelService {

    @Autowired
    EventModelMapper eventModelMapper;
    @Autowired
    JobInstService jobInstService;

    @Override
    public void save(EventModel eventModel) {
        eventModelMapper.insert(eventModel);
    }

    @Override
    public EventModel findById(Long eventId) {
        return eventModelMapper.selectById(eventId);
    }

    @Override
    public List<EventModel> findAll() {
        return eventModelMapper.selectAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testProcess(EventModel eventModel) throws Exception {
        save(eventModel);
        log.info("--------before--------");
        try {
            jobInstService.process(eventModel);
        } catch (Exception e) {
            log.error("--------error--------");
            throw new RuntimeException("error");
        } finally {
            log.info("--------after--------");
        }

    }

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testAsyncProcess(EventModel eventModel) throws Exception {
        save(eventModel);
        log.info("--------before--------");
        applicationEventPublisher.publishEvent(new InstEvent(this, eventModel));
        log.info("--------after--------");
    }

    //    @EventListener
//    @TransactionalEventListener(fallbackExecution = true)
//    public void processJobInstEvent(InstEvent instEvent) throws Exception {
//        jobInstService.asyncProcess(instEvent.getEventModel());
//    }
}
