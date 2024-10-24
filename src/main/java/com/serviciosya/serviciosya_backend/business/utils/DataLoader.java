package com.serviciosya.serviciosya_backend.business.utils;

import com.serviciosya.serviciosya_backend.business.entities.*;
import com.serviciosya.serviciosya_backend.business.managers.UsuarioMgr;
import com.serviciosya.serviciosya_backend.persistance.RubroRepository;
import com.serviciosya.serviciosya_backend.persistance.SolicitudRubroRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitudRubroRepository SolicitudRubroRepository;



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

        demandante.setRole(Usuario.Role.DEMANDANTE);
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
        ofertante.setRole(Usuario.Role.OFERTANTE);

        if (!usuarioRepository.existsByCedula(ofertante.getCedula())) {
            usuarioRepository.save(ofertante);
            System.out.println("Usuario guardado exitosamente: " + ofertante.getEmail());
        }

        Administrador admin = new Administrador(
                11111111L,
                "Juan María",
                "Pomez",
                "Avenida Italia 1234",
                "juanma.pomez@example.com",
                "099111111",
                "admin",
                new Date(), // Fecha de creación
                "Masculino",
                new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-02") // Fecha de nacimiento
        );

        admin.setRole(Usuario.Role.ADMINISTRADOR);

        if (!usuarioRepository.existsByCedula(admin.getCedula())) {
            usuarioRepository.save(admin);
            System.out.println("Usuario guardado exitosamente: " + admin.getEmail());
        }


        // Lista de nombres de rubros
        List<String> nombresRubros = Arrays.asList(
                "Limpieza", "Automóvil", "Sanitario", "Electricidad", "Arreglos",
                "Vidriería", "Carpintería", "Cerrajería", "Herrería", "Albañilería",
                "Jardinería", "Piscina"
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
//                System.out.println("Rubro ya existe: " + nombre);
            }

        });

        Iterable<Rubro> rubros = rubroRepository.findAll();
        int totalRubros = ((Collection<?>) rubros).size(); // Convertimos a Collection para obtener el tamaño
        int mitad = totalRubros / 2;
        int contador = 0;

        for (Rubro rubro : rubros) {
            SolicitudRubro.EstadoSolicitud estado;

            if (contador < mitad) {
                estado = SolicitudRubro.EstadoSolicitud.APROBADA;  // Primera mitad aceptada
            } else {
                estado = SolicitudRubro.EstadoSolicitud.PENDIENTE; // Segunda mitad pendiente
            }

            SolicitudRubro solicitud = SolicitudRubro.builder()
                    .usuarioOfertante(ofertante)
                    .rubro(rubro)
                    .estado(estado)
                    .fechaCreacion(new Date())
                    .build();

            SolicitudRubroRepository.save(solicitud);


            contador++;
        }







    }
}
