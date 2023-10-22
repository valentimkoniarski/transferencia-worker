package com.example.transferencia;

import dev.valentim.transferencia.Transferencia;
import dev.valentim.transferencia.repository.TransferenciaRepository;
import dev.valentim.usuario.Usuario;
import dev.valentim.usuario.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = {Transferencia.class, Usuario.class})
@EnableJpaRepositories(basePackageClasses = {TransferenciaRepository.class, UsuarioRepository.class})
public class TransferenciaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferenciaApplication.class, args);
    }

}
