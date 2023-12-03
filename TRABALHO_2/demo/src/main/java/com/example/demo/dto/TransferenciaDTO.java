package com.example.demo.dto;

public record TransferenciaDTO(
        Integer numAgenciaOrigem,
        Integer numContaOrigem,
        Integer numAgenciaDestino,
        Integer numContaDestino,
        Double valor
) {
}
