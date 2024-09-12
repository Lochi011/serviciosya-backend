package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findOneByCedula(Long cedula);
    Optional<Usuario> findOneByEmail(String email);

    @Query(value = "SELECT u.tipo FROM usuarios u WHERE u.id = :id", nativeQuery = true)
    String findTipoById(@Param("id") Long id);

}


