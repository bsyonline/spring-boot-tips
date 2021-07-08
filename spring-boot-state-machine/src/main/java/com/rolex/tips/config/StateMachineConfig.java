package com.rolex.tips.config;

import com.rolex.tips.Events;
import com.rolex.tips.States;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.UNPAID)   //定义了初始状态为UNPAID
                .states(EnumSet.allOf(States.class));  //指定States中的所有状态作为该状态机的状态定义。
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.UNPAID).target(States.WAITING_FOR_RECEIVE)
                .event(Events.PAY)
                .and()
                .withExternal()
                .source(States.WAITING_FOR_RECEIVE).target(States.DONE)
                .event(Events.RECEIVE);
    }
}
