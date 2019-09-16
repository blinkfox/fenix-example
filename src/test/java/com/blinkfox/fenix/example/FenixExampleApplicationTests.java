package com.blinkfox.fenix.example;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 根启动类的单测类.
 *
 * @author blinkfox on 2019-09-16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FenixExampleApplicationTests {

    /**
     * 使用一个恒为真的断言，来测试是否能正常加载 Spring Boot 的环境.
     */
    @Test
    public void contextLoads() {
        Assert.assertTrue(new Random().nextInt(2) < 3);
    }

}
