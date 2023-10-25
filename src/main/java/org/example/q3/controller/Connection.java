package org.example.q3.controller;

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

            String[] operacao = in.readUTF().split(";"); // read a line of data from the stream

            if (operacao[0].equals("deposito")) {
                String agencia = operacao[1];
                String conta = operacao[2];
                Double valor = Double.valueOf(operacao[3]);
                String response = serverController.deposito(agencia, conta, valor);
                out.writeUTF(response);
            } else if (operacao[0].equals("dados")) {
                System.out.println(serverController);
            } else {
                System.out.println("Operação inválida");
                out.writeUTF("Operação inválida");
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
