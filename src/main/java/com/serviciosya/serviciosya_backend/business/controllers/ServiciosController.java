package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.SolicitudRubro;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import com.serviciosya.serviciosya_backend.business.managers.SolicitudRubroMgr;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import com.serviciosya.serviciosya_backend.persistance.ServicioRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

// Ejemplo de un Controller en Spring Boot
@RestController
@RequestMapping("/api/verservicios")
@CrossOrigin(origins = "http://localhost:3000")
public class ServiciosController {

    private static final Logger logger = LoggerFactory.getLogger(ServiciosController.class);
    @Autowired
    private ServicioMgr servicioMgr;
    @Autowired
    private com.serviciosya.serviciosya_backend.business.utils.JwtService JwtService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/mis-servicios/")
    public ResponseEntity<List<Servicio>> obtenerServiciosDelUsuario(@RequestHeader("Authorization") String token) {
        try {

            String jwtToken = token.substring(7);
            String email = JwtService.getUsernameFromToken(jwtToken);
            UsuarioOfertante usuarioOfertante = (UsuarioOfertante) usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            List<Servicio> servicios = servicioMgr.obtenerServiciosDeUnUsuario(usuarioOfertante.getId());

            return new ResponseEntity<>(servicios, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/editar-servicios/")
    public ResponseEntity<?> editarServicio(@RequestBody Map<String, Object> payload) {
        try{
            Long id = Long.valueOf(payload.get("id").toString());
            String nombre = (String) payload.get("nombre");
            String descripcion = (String) payload.get("descripcion");
            int precio = Integer.parseInt(payload.get("precio").toString());
            String horaDesde = (String) payload.get("horaDesde");
            String horaHasta = (String) payload.get("horaHasta");
            List<String> diasSeleccionados = (List<String>) payload.get("diasSeleccionados");
            int duracionServicio = Integer.parseInt(payload.get("duracionServicio").toString());
            List<String> etiquetas = (List<String>) payload.get("etiquetas");
            System.out.println(diasSeleccionados);
            System.out.println(nombre + " este es el nombre que me llega");

            servicioMgr.modificarServicio(id,nombre,descripcion,precio,horaDesde,horaHasta,duracionServicio,etiquetas,diasSeleccionados);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @PostMapping("/borrar-servicios/")
    public ResponseEntity<?> borrarServicio(@RequestBody Map<String, Object> payload) throws EntidadNoExiste {
        try {
            Long id = Long.valueOf(payload.get("id").toString());

            servicioMgr.eliminarServicio(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
