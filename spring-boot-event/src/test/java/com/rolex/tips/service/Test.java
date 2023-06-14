/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

import com.rolex.tips.model.EventModel;
import com.rolex.tips.model.JobInst;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class Test {

    @Autowired
    EventModelService eventModelService;
    @Autowired
    JobInstService jobInstService;

    @org.junit.jupiter.api.Test
    public void test1() throws Exception {
        EventModel eventModel = new EventModel();
        eventModel.setEventId(1L);
        eventModel.setEventName("shell");
        eventModelService.testProcess(eventModel);
    }

    @org.junit.jupiter.api.Test
    public void test2() throws Exception {
        EventModel eventModel = new EventModel();
        eventModel.setEventId(1L);
        eventModel.setEventName("shell");
        eventModelService.testAsyncProcess(eventModel);
    }

}
