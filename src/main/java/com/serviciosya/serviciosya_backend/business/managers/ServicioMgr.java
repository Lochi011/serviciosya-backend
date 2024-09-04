package com.serviciosya.serviciosya_backend.business.managers;

import com.serviciosya.serviciosya_backend.business.entities.Rubro;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.SubRubro;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.persistance.RubroRepository;
import com.serviciosya.serviciosya_backend.persistance.ServicioRepository;
import com.serviciosya.serviciosya_backend.persistance.SubRubroRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioOfertanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ServicioMgr {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private UsuarioOfertanteRepository usuarioOfertanteRepository;

    @Autowired
    private SubRubroRepository subRubroRepository;


    public void publicarServicio(Map<String, Object> payload) throws InvalidInformation, EntidadNoExiste {
        // Extraer cédula del ofertante y datos del servicio
        Long cedula = Long.valueOf(payload.get("cedula").toString());
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findByCedulaWithRubros(cedula)
                .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado."));

        // Extraer datos del servicio
        Map<String, Object> servicioData = (Map<String, Object>) payload.get("servicio");
        Long subRubroId = Long.valueOf(servicioData.get("subRubroId").toString());
        SubRubro subRubro = subRubroRepository.findOneById(subRubroId)
                .orElseThrow(() -> new EntidadNoExiste("SubRubro no encontrado."));

        // Verificar que el ofertante esté habilitado para el rubro del subrubro
        boolean autorizado = ofertante.getRubros().stream()
                .anyMatch(rubro -> rubro.getId().equals(subRubro.getRubro().getId()));

        if (!autorizado) {
            throw new InvalidInformation("No autorizado para publicar en este rubro.");
        }

        // Construir y guardar el objeto Servicio
        Servicio servicio = Servicio.builder()
                .nombre((String) servicioData.get("nombre"))
                .descripcion((String) servicioData.get("descripcion"))
                .precio((Integer) servicioData.get("precio"))
                .etiquetas((List<String>) servicioData.get("etiquetas"))
                .usuarioOfertante(ofertante)
                .subRubro(subRubro)
                .build();

        servicioRepository.save(servicio);
    }
}


