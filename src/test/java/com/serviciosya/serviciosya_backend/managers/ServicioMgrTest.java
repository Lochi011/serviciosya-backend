package com.serviciosya.serviciosya_backend.managers;

import com.serviciosya.serviciosya_backend.business.entities.Rubro;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.SubRubro;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import com.serviciosya.serviciosya_backend.persistance.ServicioRepository;
import com.serviciosya.serviciosya_backend.persistance.SubRubroRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioOfertanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioMgrTest {

    @InjectMocks
    private ServicioMgr servicioMgr;

    @Mock
    private ServicioRepository servicioRepository;

    @Mock
    private UsuarioOfertanteRepository usuarioOfertanteRepository;

    @Mock
    private SubRubroRepository subRubroRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPublicarServicio_Success() throws EntidadNoExiste, InvalidInformation {
        // Configuración del ofertante y rubros
        UsuarioOfertante ofertante = new UsuarioOfertante();
        ofertante.setCedula(12345678L);
        Rubro rubro = new Rubro();
        rubro.setId(1L);
        ofertante.setRubros(List.of(rubro));

        // Configuración del subrubro
        SubRubro subRubro = new SubRubro();
        subRubro.setId(2L);
        subRubro.setRubro(rubro);

        // Mock del repositorio
        when(usuarioOfertanteRepository.findByCedulaWithRubros(12345678L)).thenReturn(Optional.of(ofertante));
        when(subRubroRepository.findByIdWithRubro(1L)).thenReturn(Optional.of(subRubro));

        // Datos del servicio para el payload
        Map<String, Object> servicioData = Map.of(
                "nombre", "Servicio Test",
                "descripcion", "Descripción del servicio",
                "precio", 100,
                "etiquetas", List.of("etiqueta1", "etiqueta2"),
                "subRubroId", 1L
        );

        // Payload con los datos del ofertante y servicio
        Map<String, Object> payload = Map.of(
                "cedula", 12345678L,
                "servicio", servicioData
        );

        // Llamar al método bajo prueba
        servicioMgr.publicarServicio(payload);

        // Verificación de que el servicio fue guardado
        verify(servicioRepository, times(1)).save(any(Servicio.class));
    }

    @Test
    public void testPublicarServicio_OfertanteNoAutorizado() {
        // Configuración del ofertante sin rubros autorizados
        UsuarioOfertante ofertante = new UsuarioOfertante();
        ofertante.setCedula(12345678L);

        // Mock del repositorio
        when(usuarioOfertanteRepository.findByCedulaWithRubros(12345678L)).thenReturn(Optional.of(ofertante));

        // Datos del servicio y subrubro no autorizado
        SubRubro subRubro = new SubRubro();
        subRubro.setRubro(new Rubro());

        when(subRubroRepository.findByIdWithRubro(1L)).thenReturn(Optional.of(subRubro));

        Map<String, Object> servicioData = Map.of("subRubroId", 1L);
        Map<String, Object> payload = Map.of("cedula", 12345678L, "servicio", servicioData);

        // Verificar que lanza una excepción
        assertThrows(InvalidInformation.class, () -> servicioMgr.publicarServicio(payload));
    }
}
