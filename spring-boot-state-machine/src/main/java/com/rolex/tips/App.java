package com.rolex.tips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import reactor.core.publisher.Mono;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
public class App implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private StateMachine<States, Events> stateMachine;

    //在run函数中，我们定义了整个流程的处理过程
    @Override
    public void run(String... args) throws Exception {
        stateMachine.startReactively();    //start()就是创建这个订单流程，根据之前的定义，该订单会处于待支付状态，
        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(Events.PAY).build()));    //通过调用sendEvent(Events.PAY)执行支付操作，
        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(Events.RECEIVE).build()));  //通过调用用sendEvent(Events.RECEIVE)来完成收货操作
    }
}
