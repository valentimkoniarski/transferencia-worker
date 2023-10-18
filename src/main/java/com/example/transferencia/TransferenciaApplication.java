package com.example.transferencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"dev.valentim.transferencia", "dev.valentim.key"})
@EnableJpaRepositories(basePackages = {"dev.valentim.transferencia", "dev.valentim.key"})
public class TransferenciaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferenciaApplication.class, args);
    }

}
