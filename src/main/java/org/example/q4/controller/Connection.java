package org.example.q4.controller;

import org.example.q4.controller.ServerController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    ServerController serverController;

    public Connection(Socket aClientSocket, ServerController serverController) {
        try {
            clientSocket = aClientSocket;
            this.serverController = serverController;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try { // an echo server
            System.out.println("Aguardando dados...");

            String clientType = in.readUTF();

            if (clientType.equals("votante")) {
                int voto = in.readInt();
                String candidato = serverController.vote(voto);
                if (candidato.equals("falha")) {
                    out.writeUTF("Erro ao registrar voto");
                } else if (candidato.equals("timer expirou")) {
                    out.writeUTF("Tempo de votação encerrado");
                } else {
                    out.writeUTF("Voto no candidato " + candidato + " registrado com sucesso");
                }

                serverController.showVotes();
            } else if (clientType.equals("votantelogin")) {
                String password = in.readUTF();
                if (password.length() == 12) {
                    out.writeUTF("Login realizado com sucesso");
                } else {
                    out.writeUTF("Falha ao fazer login");
                }
            } else {
                System.out.println("Tipo de cliente " + clientType + " não válido");
            }

        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                /* close failed */
            }
        }
    }
}
