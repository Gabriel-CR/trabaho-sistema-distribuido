package org.example.q3.model;

import org.example.q1.model.Pessoa;

public class Conta implements OperacoesBancarias {
    private int numeroConta;
    private Pessoa titularConta;
    private Double saldo;
    private Double taxaJuros;

    public Conta(int numeroConta, Pessoa titularConta, Double saldo, Double taxaJuros) {
        this.numeroConta = numeroConta;
        this.titularConta = titularConta;
        this.saldo = saldo;
        this.taxaJuros = taxaJuros;
    }

    @Override
    public void deposito(Double valor) {
        // TODO
    }

    @Override
    public void saque(Double valor) {
        // TODO
    }

    @Override
    public Double verificarSaldo() {
        // TODO
        return null;
    }

    @Override
    public void calcularJuros() {
        // TODO
    }

    @Override
    public void transferencia(Double valor, Conta sender, Conta receiver) {
        // TODO
    }

    @Override
    public void encerrarConta(Conta conta) {
        // TODO
    }
}
