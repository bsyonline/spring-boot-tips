package com.rolex.tips.event;

import org.springframework.context.ApplicationEvent;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class AsyncEvent extends ApplicationEvent {

    public AsyncEvent(Object source) {
        super(source);
    }

}
