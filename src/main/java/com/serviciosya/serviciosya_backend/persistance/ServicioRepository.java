package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServicioRepository extends CrudRepository<Servicio, Long> {

    Optional<Servicio> findOneById(Long id);




}
