package com.lawer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.lawer.mapper"})
public class LawerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LawerApplication.class, args);
    }

}
