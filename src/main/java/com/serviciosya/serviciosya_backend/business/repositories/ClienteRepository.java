package com.serviciosya.serviciosya_backend.repositories;


import com.serviciosya.serviciosya_backend.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

