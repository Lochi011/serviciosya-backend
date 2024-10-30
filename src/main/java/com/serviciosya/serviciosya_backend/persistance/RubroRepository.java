package com.serviciosya.serviciosya_backend.persistance;

import com.serviciosya.serviciosya_backend.business.entities.Rubro;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RubroRepository extends CrudRepository<Rubro, Long> {

    Optional<Rubro> findOneById(Long id);

    Optional<Rubro> findOneByNombre(String nombre);

    boolean existsByNombre(String nombre);

    Iterable<Rubro> findAll();

    @Query("SELECT r.nombre FROM Rubro r JOIN r.usuariosOfertantes o WHERE o.id = :ofertanteId")
    List<String> findNombresRubrosByOfertanteId(@Param("ofertanteId") Long ofertanteId);

    @EntityGraph(attributePaths = {"usuariosOfertantes"})
    @Query("SELECT r FROM Rubro r WHERE r.id = :id")
    Optional<Rubro> findByIdWithUsuariosOfertantes(@Param("id") Long id);

}
