package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratacionRepository extends JpaRepository<Contratacion, Long> {

    List<Contratacion> findByDemandante(UsuarioDemandante usuario);
}
