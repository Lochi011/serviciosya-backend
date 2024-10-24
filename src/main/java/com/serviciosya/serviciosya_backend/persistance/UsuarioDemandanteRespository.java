package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDemandanteRespository extends CrudRepository<UsuarioDemandante, Long> {

    Optional<UsuarioDemandante> findOneById(Long id);

    Optional<UsuarioDemandante> findOneByEmail(String email);

}

