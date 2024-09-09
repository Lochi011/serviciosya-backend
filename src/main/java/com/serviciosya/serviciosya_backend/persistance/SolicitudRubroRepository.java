package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.SolicitudRubro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SolicitudRubroRepository extends CrudRepository<SolicitudRubro, Long> {


    Optional<List<SolicitudRubro>> findAllByUsuarioOfertanteCedula(Long cedula);

    List<SolicitudRubro> findAllByRubroId(Long id);

    List<SolicitudRubro> findAllByEstado(SolicitudRubro.EstadoSolicitud estado);

    @Query("SELECT sr FROM SolicitudRubro sr JOIN FETCH sr.usuarioOfertante u JOIN FETCH sr.rubro r WHERE sr.id = :id")
    Optional<SolicitudRubro> findByIdWithUsuarioOfertanteAndRubro(@Param("id") Long id);


}