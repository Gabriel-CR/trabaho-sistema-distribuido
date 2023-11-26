package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "agencia_bancaria")
@Data
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int numero;
    @ManyToOne
    @JoinColumn(name = "banco_id", referencedColumnName = "id")
    private Banco banco;
}
