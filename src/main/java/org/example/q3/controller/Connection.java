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

            } else if (operacao[0].equals("saldo")) {
                String agencia = operacao[1];
                String conta = operacao[2];
                String response = serverController.saldo(agencia, conta);
                out.writeUTF(response);
            
            }else if (operacao[0].equals("taxaJuros")) {
                String agencia = operacao[1];
                String conta = operacao[2];

                String response = serverController.taxaJuros(agencia, conta);
                out.writeUTF(response);
            
            }else if (operacao[0].equals("calcularJuros")) {
                String agencia = operacao[1];
                String conta = operacao[2];
                String response = serverController.calcularJuros(agencia, conta);
                out.writeUTF(response);
            
            } else if (operacao[0].equals("saque")) {
                String agencia = operacao[1];
                String conta = operacao[2];
                Double valor = Double.valueOf(operacao[3]);
                String response = serverController.saque(agencia, conta, valor);
                out.writeUTF(response);
            
            } else if (operacao[0].equals("transferir")) {
                String agencia = operacao[1];
                String conta = operacao[2];
                Double valor = Double.valueOf(operacao[3]);
                String agenciaDestino = operacao[4];
                String contaDestino = operacao[5];
                String response = serverController.transferir(agencia, conta, valor, agenciaDestino, contaDestino);
                out.writeUTF(response);
            
            } else if(operacao[0].equals("adicionarConta")){
                String agencia = operacao[1];
                String conta = operacao[2];
                String nome = operacao[3];
                Double saldo = Double.valueOf(operacao[4]);
                String response = serverController.adicionarConta(agencia, conta, nome, saldo);

                out.writeUTF(response);
           
            } else if(operacao[0].equals("encerrarConta")){
                String agencia = operacao[1];
                String numeroConta = operacao[2];

                String response = serverController.encerrarConta(agencia, numeroConta);

                out.writeUTF(response);

            }else {
                out.writeUTF("Operação não encontrada");
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
