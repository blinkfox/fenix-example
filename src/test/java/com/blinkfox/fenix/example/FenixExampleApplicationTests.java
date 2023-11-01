package com.blinkfox.fenix.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 根启动类的单测类.
 *
 * @author blinkfox on 2019-09-16.
 */
class FenixExampleApplicationTests {

    /**
     * 使用一个恒为真的断言，来测试是否能正常加载 Spring Boot 的环境.
     */
    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
