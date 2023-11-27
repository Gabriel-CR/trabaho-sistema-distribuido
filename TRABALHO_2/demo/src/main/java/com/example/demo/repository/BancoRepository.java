package com.example.demo.repository;

import com.example.demo.entity.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<Banco, Integer> {
}
