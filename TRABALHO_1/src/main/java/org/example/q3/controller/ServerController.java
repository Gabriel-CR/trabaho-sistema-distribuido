package org.example.q3.controller;

import org.example.q3.model.Banco;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    private final int PORT = 9000;
    private Banco banco;

    public ServerController() {
        banco = new Banco("BB");
    }

    public void serverRun() {
        try {
            System.out.println("Aguardando conexão...");
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket client = serverSocket.accept();

                System.out.println("Conexão estabelecida");
                Connection c = new Connection(client, this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String deposito(String agenciaId, String contaId, Double valor) {
        return this.banco.deposito(agenciaId, contaId, valor);
    }

    public String saque(String agenciaId, String contaId, Double valor) {
        return this.banco.saque(agenciaId, contaId, valor);
    }

    public String saldo(String agenciaId, String contaId) {
        return this.banco.saldo(agenciaId, contaId);
    }

    public String taxaJuros(String agenciaId, String contaId) {
        return this.banco.taxaJuros(agenciaId, contaId);
    }

    public String calcularJuros(String agenciaId, String contaId) {
        return this.banco.calcularJuros(agenciaId, contaId);
    }

    public String transferir(String agenciaId, String contaId, Double valor, String agenciaIdDestino, String contaIdDestino) {
        return this.banco.transferir(agenciaId, contaId, valor, agenciaIdDestino, contaIdDestino);
    }

    public String adicionarConta(String tipo, String agenciaId, String contaId, String nome, Double saldo) {
        return this.banco.adicionarConta(tipo, agenciaId, contaId, nome, saldo);
    }

    public String encerrarConta(String agenciaId, String contaId) {
        return this.banco.encerrarConta(agenciaId, contaId);
    }

    @Override
    public String toString() {
        return banco.toString();
    }
}
