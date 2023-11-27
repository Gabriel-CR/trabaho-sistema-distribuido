package org.example.q3.model;

import org.example.q1.model.Pessoa;

public class ContaPoupancao extends Conta {
    // TODO: implementar métodos especificos desta conta

    public ContaPoupancao(String numeroConta, String titularConta, Double saldo, Double taxaJuros) {
        super(numeroConta, titularConta, saldo, taxaJuros);
    }

    @Override
    public String toString() {
        return "tipo: conta poupanca, numero: " + this.getNumeroConta() + ", nome: " + this.getTitularConta() + ", saldo: R$" + this.getSaldo();
    }


    @Override
    public void calcularJuros(){
        this.setSaldo(this.getSaldo() + ( 1 + this.getTaxaJuros()/100));
    }
}
