package org.example.q3.model;

public interface OperacoesBancarias {
    void deposito(Double valor);
    void saque(Double valor);
    Double verificarSaldo();
    Double verificarTaxaJuros();
    void calcularJuros();
    void transferencia(Double valor, Conta contaDestino);
    void encerrarConta(Conta conta);
}
