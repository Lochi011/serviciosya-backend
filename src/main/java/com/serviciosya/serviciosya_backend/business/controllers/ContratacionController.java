package com.serviciosya.serviciosya_backend.controllers;

import com.serviciosya.serviciosya_backend.business.controllers.SolicitudRubroController;
import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.ContratacionMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contrataciones")
@CrossOrigin(origins = "http://localhost:3000")
public class ContratacionController {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudRubroController.class);

    @Autowired
    private ContratacionMgr contratacionMgr;

    /**
     * Crea una nueva contratación.
     *
     * @param idDemandante ID del demandante.
     * @param idOfertante ID del ofertante.
     * @param idServicio ID del servicio.
     * @param fecha Fecha de la contratación en formato YYYY-MM-DD.
     * @param direccion Dirección donde se prestará el servicio.
     * @param apartamento Apartamento (opcional).
     * @param hora Hora de la contratación.
     * @param comentario Comentario adicional.
     * @return Mensaje de éxito o error.
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crearContratacion(@RequestParam Long idDemandante,
                                                    @RequestParam Long idOfertante,
                                                    @RequestParam Long idServicio,
                                                    @RequestParam String fecha,
                                                    @RequestParam String direccion,
                                                    @RequestParam(required = false) String apartamento,
                                                    @RequestParam String hora,
                                                    @RequestParam String comentario) {
        try {
            LocalDate fechaServicio = LocalDate.parse(fecha);
            contratacionMgr.crearContratacion(idDemandante, idOfertante, fechaServicio, direccion, apartamento, hora, comentario, idServicio);
            return new ResponseEntity<>("Contratación creada exitosamente.", HttpStatus.CREATED);
        } catch (EntidadNoExiste | InvalidInformation e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la contratación.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }}


