package org.example.q4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Candidato {
    private String nome;
    private int numero;

    public Candidato() {
    }

    public Candidato(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    @JsonIgnore
    public Candidato getCandidato() {
        return this;
    }

    @Override
    public String toString() {
        return "[" + numero + "] " + nome;
    }
}
