package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findOneByCedula(Long cedula);

    Optional<Usuario> findOneByEmail(String email);

}
