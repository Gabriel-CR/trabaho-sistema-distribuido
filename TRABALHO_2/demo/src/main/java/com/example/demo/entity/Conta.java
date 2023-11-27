package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "conta")
@Data
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numeroConta;
    private String titularConta;
    private Double saldo;
    private Double taxaJuros;
    private String tipo;
    private Integer idAgencia;
}
