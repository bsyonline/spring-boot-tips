package com.rolex.tips.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
public class PostConstructorTest {
    @PostConstruct
    public void test(){
        log.info("PostConstructTest");
    }
}
