package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.Notificacion;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface NotificacionRepository extends CrudRepository<Notificacion, Long> {

    @Override
    Optional<Notificacion> findById(Long aLong);

    Optional<List<Notificacion>> findByUsuarioOfertanteAndLeidoFalse(UsuarioOfertante usuarioOfertante);

    Optional<List<Notificacion>> findByUsuarioOfertante(UsuarioOfertante usuarioOfertante);
}
