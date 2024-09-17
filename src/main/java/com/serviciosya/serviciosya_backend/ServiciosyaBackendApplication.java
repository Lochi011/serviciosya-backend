package com.serviciosya.serviciosya_backend;

import com.serviciosya.serviciosya_backend.business.entities.SolicitudRubro;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.managers.UsuarioMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class ServiciosyaBackendApplication {

	@Autowired
	private UsuarioMgr usuarioMgr;

	public static void main(String[] args) {
		SpringApplication.run(ServiciosyaBackendApplication.class, args);

	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			// Crear usuarios de prueba
			createTestUsers();
		};
	}

	//Long cedula, String nombre, String apellido, String direccion, String email, String telefono, String contrasena, Date fechaCreacion, String genero, Date fechaNacimiento) {
//
	private void createTestUsers() {
		try {
			UsuarioOfertante ofertante2 = new UsuarioOfertante(
					53821485L,
					"Facundo",
					"Pellistri",
					"Avenida Facundo 742",
					"facu.pellistri@example.com",
					"099598360",
					"contrasena",
					new Date(), // Fecha de creación
					"Masculino",
					new SimpleDateFormat("yyyy-MM-dd").parse("1985-05-05") // Fecha de nacimiento
			);
			usuarioMgr.agregarUsuario(ofertante2);
			System.out.println("Usuario agregado con exito");
			// Crear un Usuario Demandante de prueba
			UsuarioDemandante demandante = new UsuarioDemandante(
					12345678L,
					"Juan",
					"Pérez",
					"Calle Falsa 123",
					"juan.perez@example.com",
					"099123456",
					"password123",
					new Date(), // Fecha de creación
					"Masculino",
					new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01") // Fecha de nacimiento
			);

			usuarioMgr.agregarUsuario(demandante);

			// Crear un Usuario Ofertante de prueba
			UsuarioOfertante ofertante = new UsuarioOfertante(
					87654321L,
					"María",
					"Gómez",
					"Avenida Siempreviva 742",
					"maria.gomez@example.com",
					"099654321",
					"password456",
					new Date(), // Fecha de creación
					"Femenino",
					new SimpleDateFormat("yyyy-MM-dd").parse("1985-05-05") // Fecha de nacimiento
			);


			usuarioMgr.agregarUsuario(ofertante);


			System.out.println("Usuarios de prueba creados exitosamente");

		} catch (Exception e) {
			System.err.println("Error creando usuarios de prueba: " + e.getMessage());
		}

	}




}
