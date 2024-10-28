package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.NotificacionDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NotificacionDemandanteRepository extends CrudRepository<NotificacionDemandante, Long> {

    Optional<List<NotificacionDemandante>> findByUsuarioDemandanteAndLeidoFalse(UsuarioDemandante usuarioDemandante);

    Long countByUsuarioDemandanteAndLeidoFalse(UsuarioDemandante usuarioDemandante);
}
