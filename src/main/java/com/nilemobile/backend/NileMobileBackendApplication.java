package com.nilemobile.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.nilemobile")
public class NileMobileBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NileMobileBackendApplication.class, args);
    }

}
