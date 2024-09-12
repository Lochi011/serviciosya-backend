package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.Rubro;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RubroRepository extends CrudRepository<Rubro, Long> {

    Optional<Rubro> findOneById(Long id);

    Optional<Rubro> findOneByNombre(String nombre);
}
