package com.example.demo.repository;

import com.example.demo.entity.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenciaRepository extends JpaRepository<Agencia, Integer> {
}
