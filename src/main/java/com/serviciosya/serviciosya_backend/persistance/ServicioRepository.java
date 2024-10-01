package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.Rubro;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ServicioRepository extends CrudRepository<Servicio, Long> {

    Optional<Servicio> findOneById(Long id);


    boolean existsByNombreAndDescripcionAndPrecioAndHoraDesdeAndHoraHastaAndUsuarioOfertanteAndRubro(String tituloServicio, String descripcion, int precio, String horaDesde, String horaHasta, UsuarioOfertante ofertante, Rubro rubro);

    Optional<List<Servicio>> findAllByRubro(Rubro rubro);
}
