package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.SubRubro;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubRubroRepository extends CrudRepository<SubRubro, Long> {

    Optional<SubRubro> findOneById(Long id);
}
