package com.gmail.bishoybasily.licensing.application;

import com.gmail.bishoybasily.licensing.enforcer.config.LicensingEnforcerProps;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(LicensingEnforcerProps props) {
        return args -> {
            System.out.println(props);
        };
    }

}