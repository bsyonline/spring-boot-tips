package com.rolex.tips.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
public class EventListenerDemo {

    @EventListener
    @Order(1)
    public void test1(ContextRefreshedEvent event){
        log.info("EventListener1");
    }

    @EventListener
    @Order(2)
    public void test2(ContextRefreshedEvent event){
        log.info("EventListener2");
    }

    @EventListener
    @Order(1)
    public void test3(ContextRefreshedEvent event){
        log.info("EventListener3");
    }

}
