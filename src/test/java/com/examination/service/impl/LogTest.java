package com.examination.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/28 14:59
 */
@SpringBootTest
@Slf4j
public class LogTest {

    @Test
    public void test() {
        String name = "test";
        String password = "123456";
        log.debug("debug...");
        log.info("name：{}, password：{}", name, password);
        log.info("info...");
        log.error("error...");
        log.warn("warning...");
    }

}
