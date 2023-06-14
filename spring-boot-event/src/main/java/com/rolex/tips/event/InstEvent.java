package com.rolex.tips.event;

import com.rolex.tips.model.EventModel;
import org.springframework.context.ApplicationEvent;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class InstEvent extends ApplicationEvent {
    EventModel eventModel;

    public InstEvent(Object source, EventModel eventModel) {
        super(source);
        this.eventModel = eventModel;
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }
}
