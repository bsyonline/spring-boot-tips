package com.rolex.tips.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
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
public class BeanTest {

    @Bean(initMethod = "init")
    public InitMethodBeanTest initMethodBeanTest() {
        return new InitMethodBeanTest();
    }

    class InitMethodBeanTest {
        public void init() {
            log.info("InitMethodBeanTest");
        }
    }

}
