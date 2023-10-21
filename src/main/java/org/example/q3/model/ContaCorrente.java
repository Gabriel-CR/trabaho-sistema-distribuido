package org.example.q3.model;

import org.example.q1.model.Pessoa;

public class ContaCorrente extends Conta {
    // TODO: implementar métodos especificos desta conta

    public ContaCorrente(int numeroConta, Pessoa titularConta, Double saldo, Double taxaJuros) {
        super(numeroConta, titularConta, saldo, taxaJuros);
    }
}
