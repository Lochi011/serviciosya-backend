package com.serviciosya.serviciosya_backend.persistance;

// UsuarioOfertanteRepository.java

import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioOfertanteRepository extends CrudRepository<UsuarioOfertante, Long> {

    // Query para obtener el UsuarioOfertante junto con sus rubros a partir de la cédula
    @Query("SELECT u FROM UsuarioOfertante u LEFT JOIN FETCH u.rubros WHERE u.cedula = :cedula")
    Optional<UsuarioOfertante> findByCedulaWithRubros(Long cedula);

    Optional<UsuarioOfertante> findOneByCedula(Long cedula);

    Optional<UsuarioOfertante> findOneById(Long id);

    Optional<UsuarioOfertante> findOneByEmail(String email);


}
