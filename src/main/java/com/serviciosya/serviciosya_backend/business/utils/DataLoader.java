package com.serviciosya.serviciosya_backend.business.utils;

import com.serviciosya.serviciosya_backend.business.entities.*;
import com.serviciosya.serviciosya_backend.business.managers.ContratacionMgr;
import com.serviciosya.serviciosya_backend.business.managers.SolicitudRubroMgr;
import com.serviciosya.serviciosya_backend.persistance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.serviciosya.serviciosya_backend.business.entities.Contratacion.EstadoContratacion.PENDIENTE;



import java.util.Random;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitudRubroRepository SolicitudRubroRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ContratacionRepository contratacionRepository;

    @Autowired
    private UsuarioOfertanteRepository usuarioOfertanteRepository;

    @Autowired
    private ContratacionMgr contratacionMgr;

    @Autowired
    private SolicitudRubroMgr solicitudRubroMgr;




    @Override
    public void run(String... args) throws Exception {



        if (usuarioRepository.existsByCedula(12345678L)) {
            System.out.println("Usuario de prueba ya existe. No se ejecuta el DataLoader, borre este usuario o la base de datos completa para ejecutar el data loader");
            return;
        }

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
        int mitad = 6;
        int contador = 0;
        UsuarioOfertante usuarioOfertante = usuarioOfertanteRepository.findByIdWithRubros(ofertante.getId()).orElseThrow();
        System.out.println("Usuario ofertante: " + usuarioOfertante.getRubros());
        for (Rubro rubro1 : rubros) {
            Rubro rubro = rubroRepository.findByIdWithUsuariosOfertantes(rubro1.getId()).orElseThrow();
            SolicitudRubro solicitud = SolicitudRubro.builder()
                    .usuarioOfertante(usuarioOfertante)
                    .rubro(rubro)
                    .estado(SolicitudRubro.EstadoSolicitud.PENDIENTE)
                    .fechaCreacion(new Date())
                    .build();
            SolicitudRubroRepository.save(solicitud);

            if (contador < mitad) {
//                solicitud.setEstado(SolicitudRubro.EstadoSolicitud.APROBADA);/Primera mitad aceptada
                solicitudRubroMgr.aprobarSolicitud(solicitud.getId());
            } else {
                ; // Segunda mitad pendiente
            }
            System.out.println("Usuario ofertante: " + usuarioOfertante.getRubros());

            usuarioOfertante.agregarRubro(rubro);





            contador++;
        }

        Rubro rubroLimpieza = rubroRepository.findById(1L).orElseThrow(); // Asumiendo que el id del rubro "Limpieza" es 1

        // Lista de etiquetas para servicios de limpieza
        List<String> etiquetasLimpieza = Arrays.asList(
                "Limpieza profunda",
                "Limpieza de oficina",
                "Limpieza de hogar",
                "Limpieza de vidrios",
                "Limpieza de alfombras",
                "Otros"
        );

        // Lista de nombres de servicios únicos
        List<String> nombresServicios = Arrays.asList(
                "Limpieza de vidrios y ventanas",
                "Limpieza profunda de baños y cocinas",
                "Limpieza de alfombras y tapicería",
                "Limpieza de oficinas corporativas",
                "Limpieza general del hogar",
                "Limpieza de exteriores y jardines",
                "Desinfección y control de plagas",
                "Limpieza por horas en hogares"
        );

        // Usaremos la cantidad exacta de etiquetas + 2 servicios adicionales
        int numServicios = etiquetasLimpieza.size() + 2;

        // Iterar sobre el número de servicios
        for (int i = 0; i < numServicios; i++) {
            String nombreServicio;
            List<String> etiquetas;

            // Asignar un nombre de servicio y etiquetas correspondientes
            if (i < etiquetasLimpieza.size()) {
                // Un servicio para cada etiqueta
                nombreServicio = nombresServicios.get(i);
                etiquetas = Arrays.asList(etiquetasLimpieza.get(i)); // Asigna la etiqueta específica
            } else {
                // Servicios adicionales con etiquetas generales
                nombreServicio = nombresServicios.get(i);
                etiquetas = Arrays.asList("Otros"); // Etiqueta "Otros" para servicios adicionales
            }

            // Generar el servicio
            Servicio servicio = Servicio.builder()
                    .nombre(nombreServicio)
                    .descripcion("Descripción del servicio de " + nombreServicio)
                    .precio((int) generarPrecioAleatorio())
                    .horaDesde("09:00")  // Hora de inicio fija o configurable
                    .horaHasta("18:00")  // Hora de fin fija o configurable
                    .diasSeleccionados(Arrays.asList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes"))  // Días laborales
                    .duracionServicio(60 + new Random().nextInt(60))  // Duración del servicio entre 2 y 4 horas
                    .etiquetas(etiquetas)  // Asigna las etiquetas correspondientes
                    .usuarioOfertante(ofertante)  // Asigna el ofertante
                    .rubro(rubroLimpieza)  // Rubro de limpieza
                    .fechaCreacion(new Date())  // Fecha de creación actual
                    .descripcion("Descripción del servicio de " + nombreServicio)
                    .build();

            // Guardar el servicio en la base de datos
            servicioRepository.save(servicio);

            Contratacion contratacion = Contratacion.builder()
                    .demandante(demandante)
                    .ofertante(ofertante)
                    .servicio(servicio)
                    .fechaContratacion( generarFechaAleatoria())
                    .direccion(ofertante.getDireccion())
                    .hora(String.valueOf(generarHoraAleatoria()))
                    .comentario("Quiero contratar el servicio llamado " + servicio.getNombre())
                    .estado(PENDIENTE)
                    .apartamento(ofertante.getDireccion())
                    .direccion(ofertante.getDireccion())
                    .build();

            contratacionRepository.save(contratacion);
        }

        Rubro rubroAutomovil = rubroRepository.findById(2L).orElseThrow(); // Asumiendo que el id del rubro "Automóvil" es 2

        // Lista de etiquetas para servicios de automóvil
        List<String> etiquetasAutomovil = Arrays.asList(
                "Lavado",
                "Pintura",
                "Motor",
                "Cambio de aceite",
                "Neumáticos",
                "Frenos",
                "Suspensión",
                "Alineación y balanceo",
                "Batería",
                "Aire acondicionado",
                "Otros"
        );

        // Lista de nombres de servicios únicos para automóvil
        List<String> nombresServicios2 = Arrays.asList(
                "Lavado completo de vehículo",
                "Pintura de carrocería",
                "Revisión y ajuste del motor",
                "Cambio de aceite y filtro",
                "Cambio de neumáticos",
                "Revisión y ajuste de frenos",
                "Reparación de suspensión",
                "Alineación y balanceo",
                "Revisión de batería y sistema eléctrico",
                "Revisión y recarga de aire acondicionado",
                "Otros servicios automotrices generales"
        );

        // Número de servicios a crear: 1 por etiqueta más 2 adicionales (mínimo 13 servicios)
        int numServicios2 = etiquetasAutomovil.size() + 2;


        // Iterar sobre el número de servicios
        for (int i = 0; i < numServicios2; i++) {

            String nombreServicio;
            List<String> etiquetas;

            // Asignar un nombre de servicio y etiquetas correspondientes
            if (i < etiquetasAutomovil.size()) {
                // Un servicio para cada etiqueta
                nombreServicio = nombresServicios2.get(i);
                etiquetas = Arrays.asList(etiquetasAutomovil.get(i)); // Asigna la etiqueta específica
            } else {
                // Servicios adicionales con etiquetas generales
                nombreServicio = "Servicio adicional de automóvil " + (i - etiquetasAutomovil.size() + 1);
                etiquetas = Arrays.asList("Otros"); // Etiqueta "Otros" para servicios adicionales
            }

            // Crear el servicio para Automóvil
            Servicio servicio = Servicio.builder()
                    .nombre(nombreServicio)
                    .descripcion("Descripción del servicio de " + nombreServicio)
                    .precio((int) generarPrecioAleatorio2())
                    .horaDesde("09:00")  // Hora de inicio fija o configurable
                    .horaHasta("18:00")  // Hora de fin fija o configurable
                    .diasSeleccionados(Arrays.asList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sabadoo", "Domingo"))  // Días laborales
                    .duracionServicio(2 + new Random().nextInt(3))  // Duración del servicio entre 2 y 4 horas
                    .etiquetas(etiquetas)  // Asigna las etiquetas correspondientes
                    .usuarioOfertante(ofertante)  // Asigna el ofertante
                    .rubro(rubroAutomovil)  // Rubro de Automóvil
                    .fechaCreacion(new Date())  // Fecha de creación actual
                    .build();

            // Guardar el servicio en la base de datos
            servicioRepository.save(servicio);

            Long demandanteId = demandante.getId();
            Long ofertanteId = ofertante.getId();
            LocalDate fechaContratacion = generarFechaAleatoria();
            String direccion = demandante.getDireccion();
            String apartamento = demandante.getDireccion();
            String hora = String.valueOf(generarHoraAleatoria());
            String comentario = "Quiero contratar el servicio llamado " + servicio.getNombre();
            Long servicioId = servicio.getId();

            contratacionMgr.crearContratacion(demandanteId, ofertanteId, fechaContratacion, direccion, apartamento, hora, comentario, servicioId);




        }

        Iterable<Contratacion> contrataciones = contratacionRepository.findAll();
        Random random = new Random();
        int contactCount = 0;
        int rejectCount = 0;

        // First pass: ensure at least one contact and one reject
        for (Contratacion contratacion : contrataciones) {
            if (contactCount == 0) {
                String nombreServicio = contratacion.getServicio().getNombre();
                String mensaje = String.format("Hola, por favor contactame para que pueda brindarte el servicio %s.", nombreServicio);
                contratacionMgr.contactarContratacion(contratacion.getId(), mensaje, "12345678", "maria.gomez@example.com");
                contactCount++;
            } else if (rejectCount == 0) {
                contratacionMgr.rechazarContratacion(contratacion.getId(), "No puedo brindar el servicio en la fecha solicitada.");
                rejectCount++;
            } else {
                // Randomly assign the status based on probabilities
                int chance = random.nextInt(100);
                if (chance < 25) {
                    contratacionMgr.rechazarContratacion(contratacion.getId(), "No puedo brindar el servicio en la fecha solicitada.");
                    rejectCount++;
                } else if (chance < 50) {
                    String nombreServicio = contratacion.getServicio().getNombre();
                    String mensaje = String.format("Hola, por favor contactame para que pueda brindarte el servicio %s.", nombreServicio);
                    contratacionMgr.contactarContratacion(contratacion.getId(), mensaje, "12345678", "maria.gomez@example.com");

                    contactCount++;
                } else {
                    contratacion.setEstado(Contratacion.EstadoContratacion.TERMINADA);
                   contratacionRepository.save(contratacion);
                }
            }
        }
    }





    // Función para generar un precio aleatorio entre $30 y $500
    private static double generarPrecioAleatorio2() {
        Random random = new Random();
        return 30 + (500 - 30) * random.nextDouble(); // Precio entre $30 y $500
    }


    // Función para generar un precio aleatorio entre $30 y $200
    private static double generarPrecioAleatorio() {
        Random random = new Random();
        return 30 + (200 - 30) * random.nextDouble(); // Precio entre $30 y $200
    }



    public LocalTime generarHoraAleatoria() {
        // Definir el rango de horas (entre las 08:00 y las 18:00)
        int horaInicio = 8;  // 8 AM
        int horaFin = 18;    // 6 PM

        // Generar una hora aleatoria entre la hora de inicio y fin
        int randomHour = ThreadLocalRandom.current().nextInt(horaInicio, horaFin);
        int randomMinute = ThreadLocalRandom.current().nextInt(0, 60);

        return LocalTime.of(randomHour, randomMinute);
    }


    public LocalDate generarFechaAleatoria() {
        // Definir el rango de fechas (hoy hasta 30 días después)
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(30);

        long startEpochDay = fechaInicio.toEpochDay();
        long endEpochDay = fechaFin.toEpochDay();

        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }


}

