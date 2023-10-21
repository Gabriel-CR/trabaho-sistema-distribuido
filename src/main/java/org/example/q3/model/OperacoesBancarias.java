package org.example.q3.model;

public interface OperacoesBancarias {
    void deposito(Double valor);
    void saque(Double valor);
    Double verificarSaldo();
    void calcularJuros();
    void transferencia(Double valor, Conta sender, Conta receiver);
    void encerrarConta(Conta conta);
}
