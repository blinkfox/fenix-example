package com.blinkfox.fenix.example;

import com.blinkfox.fenix.EnableFenix;
import com.blinkfox.fenix.jpa.FenixJpaRepositoryFactoryBean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 请在 Spring Boot 应用中配置 {@link EnableJpaRepositories#repositoryFactoryBeanClass}
 * 的值为 {@link FenixJpaRepositoryFactoryBean}.
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
