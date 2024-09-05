package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.SubRubro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubRubroRepository extends CrudRepository<SubRubro, Long> {

    Optional<SubRubro> findOneById(Long id);

    @Query("SELECT sr FROM SubRubro sr JOIN FETCH sr.rubro WHERE sr.id = :id")
    Optional<SubRubro> findByIdWithRubro(@Param("id") Long id);

}
