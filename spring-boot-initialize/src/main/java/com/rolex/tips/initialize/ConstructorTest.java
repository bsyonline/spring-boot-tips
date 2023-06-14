package com.rolex.tips.initialize;

import lombok.extern.slf4j.Slf4j;
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
public class ConstructorTest {
    public ConstructorTest() {
        log.info("ConstructorTest");
    }
}
