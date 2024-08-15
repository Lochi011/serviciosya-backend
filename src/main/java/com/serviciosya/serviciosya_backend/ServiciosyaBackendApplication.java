package com.serviciosya.serviciosya_backend;

import com.serviciosya.serviciosya_backend.entities.Cliente;
import com.serviciosya.serviciosya_backend.repositories.ClienteRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ServiciosyaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiciosyaBackendApplication.class, args);

	}


}
