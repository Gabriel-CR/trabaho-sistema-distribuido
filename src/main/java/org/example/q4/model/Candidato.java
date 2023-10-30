package org.example.q4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Candidato {
    private String nome;
    private int numero;
    private int votos;

    public Candidato() {
    }

    public Candidato(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
        this.votos = 0;
    }

    public void vote() {
        this.votos += 1;
    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public int getVotos() {
        return votos;
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
