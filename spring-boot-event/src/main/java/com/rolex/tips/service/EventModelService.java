package com.rolex.tips.service;

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
public interface EventModelService {
    void save(EventModel eventModel);

    EventModel findById(Long eventId);

    List<EventModel> findAll();

    void testProcess(EventModel eventModel) throws Exception;

    void testAsyncProcess(EventModel eventModel) throws Exception;
}
