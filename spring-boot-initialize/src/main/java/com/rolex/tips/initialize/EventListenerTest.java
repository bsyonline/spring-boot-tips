package com.rolex.tips.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
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
public class EventListenerTest {

    @EventListener
    public void test(ContextRefreshedEvent event){
        log.info("EventListenerTest");
    }

}
