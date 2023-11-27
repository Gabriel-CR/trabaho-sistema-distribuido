package org.example.q3.model;

import org.example.q1.model.Pessoa;

public class Conta implements OperacoesBancarias {
    private String numeroConta;
    private String titularConta;
    private Double saldo;
    private Double taxaJuros;

    public Conta(String numeroConta, String titularConta, Double saldo, Double taxaJuros) {
        this.numeroConta = numeroConta;
        this.titularConta = titularConta;
        this.saldo = saldo;
        this.taxaJuros = taxaJuros;
    }

    @Override
    public void deposito(Double valor) {
        this.saldo += valor;
    }

    @Override
    public void saque(Double valor) {
        if(valor > this.saldo) {
            throw new RuntimeException("Saldo insuficiente");
        }else {
            this.saldo -= valor;
        }
    }

    @Override
    public void transferencia(Double valor, Conta contaDestino) {
        if(valor > this.saldo) {
            throw new RuntimeException("Saldo insuficiente");
        }else {
            this.saldo -= valor;
            contaDestino.deposito(valor);
        }
    }

    @Override
    public Double verificarSaldo() {
        return this.saldo;
    }

    @Override
    public Double verificarTaxaJuros() {
        return this.taxaJuros;
    }

    @Override
    public void calcularJuros() {
        return;
    }

    @Override
    public void encerrarConta(Conta conta) {
        // TODO
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    @Override
    public String toString() {
        return "numero: " + numeroConta + ", nome: " + titularConta + ", saldo: R$" + saldo;
    }

    public String getTitularConta() {
        return titularConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public Double getTaxaJuros() {
        return taxaJuros;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
