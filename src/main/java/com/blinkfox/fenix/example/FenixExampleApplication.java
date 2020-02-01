package com.blinkfox.fenix.example;

import com.blinkfox.fenix.EnableFenix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 请在 Spring Boot 应用中标注 {code @EnableFenix} 注解.
 *
 * @author blinkfox on 2019-08-16.
 */
@EnableFenix
@SpringBootApplication
public class FenixExampleApplication {

    /**
     * SpringBoot 启动的主入口方法.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(FenixExampleApplication.class, args);
    }

}
