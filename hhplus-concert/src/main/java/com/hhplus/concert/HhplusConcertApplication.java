package com.hhplus.concert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HhplusConcertApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhplusConcertApplication.class, args);
    }

}
