package org.example.q3.model;

import org.example.q1.model.Pessoa;

public class ContaCorrente extends Conta {
    // TODO: implementar métodos especificos desta conta

    public ContaCorrente(String numeroConta, String titularConta, Double saldo, Double taxaJuros) {
        super(numeroConta, titularConta, saldo, taxaJuros);
    }

    // TODO: implementar métodos especificos desta conta
    @Override
    public String toString() {
        return "tipo: conta corrente, numero: " + this.getNumeroConta() + ", nome: " + this.getTitularConta() + ", saldo: R$" + this.getSaldo();
    }
}
