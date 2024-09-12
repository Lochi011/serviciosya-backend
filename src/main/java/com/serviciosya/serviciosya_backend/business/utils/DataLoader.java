package com.serviciosya.serviciosya_backend.business.utils;

import com.serviciosya.serviciosya_backend.business.entities.Rubro;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.managers.UsuarioMgr;
import com.serviciosya.serviciosya_backend.persistance.RubroRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;



    @Override
    public void run(String... args) throws Exception {

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
        if (!usuarioRepository.existsByCedula(demandante.getCedula())) {
            usuarioRepository.save(demandante);
            System.out.println("Usuario guardado exitosamente: " + demandante.getEmail());
        }


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

        if (!usuarioRepository.existsByCedula(ofertante.getCedula())) {
            usuarioRepository.save(ofertante);
            System.out.println("Usuario guardado exitosamente: " + ofertante.getEmail());
        }


        // Lista de nombres de rubros
        List<String> nombresRubros = Arrays.asList(
                "Vidriero", "Herrero", "Sanitario", "Vehicular", "Limpieza",
                "Carpintero", "Cerrajero", "Electricista", "Albanileria",
                "Jardinero", "Piscinero", "Tecnico (Arreglos)"
        );

        // Cargar rubros si no existen
        nombresRubros.forEach(nombre -> {
            if (!rubroRepository.existsByNombre(nombre)) {
                Rubro rubro = Rubro.builder()
                        .nombre(nombre)
                        .solicitudesRubro(new ArrayList<>())
                        .usuariosOfertantes(new ArrayList<>())
                        .build();
                rubroRepository.save(rubro);

                System.out.println("Rubro creado: " + rubro.getNombre());
            } else {
                System.out.println("Rubro ya existe: " + nombre);
            }
        });
    }
}
