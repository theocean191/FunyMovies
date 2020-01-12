package com.lhmai.funnytube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FunnytubeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunnytubeApplication.class, args);
    }

}
