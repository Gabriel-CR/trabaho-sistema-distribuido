package org.example.q3.controller;

import org.example.q3.controller.Connection;
import org.example.q3.model.Banco;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    private Banco banco;

    public ServerController() {
        banco = new Banco("BB");
    }

    public void serverRun() {
        try {
            System.out.println("Aguardando conexão...");
            ServerSocket serverSocket = new ServerSocket(9000);

            while (true) {
                Socket client = serverSocket.accept();

                System.out.println("Conexão estabelecida");
                Connection c = new Connection(client, this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deposito(String agenciaId, String contaId, Double valor) {
        this.banco.deposito(agenciaId, contaId, valor);
        // TODO: retorna um valor de sucesso/erro para o cliente
        System.out.println("depósito concluído");
    }

    @Override
    public String toString() {
        return banco.toString();
    }
}
