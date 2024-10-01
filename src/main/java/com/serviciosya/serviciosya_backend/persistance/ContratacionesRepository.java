package com.serviciosya.serviciosya_backend.persistance;
import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContratacionesRepository extends JpaRepository {
    @Query("SELECT u.id AS usuarioId, u.apellido AS apellidoUsuario, s.nombre AS nombreServicio, s.precio AS precioServicio, u_ofertante.nombre AS nombreOfertante "
            + "FROM Contrataciones c "
            + "JOIN usuarios u ON c.usuario_id = u.id "
            + "JOIN Servicios s ON c.servicio_id = s.id "
            + "JOIN Usuarios u_ofertante ON s.usuario_ofertante_id = u_ofertante.id")
    List<Object[]> findContratacionesConServiciosYUsuarios();
}
