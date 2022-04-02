package com.vivacon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class VivaconApplication {

    public static void main(String[] args) {
        SpringApplication.run(VivaconApplication.class, args);
    }
}
