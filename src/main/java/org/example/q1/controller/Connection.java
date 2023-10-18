package org.example.q1.controller;

import org.example.q1.model.Pessoa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    PessoasOutputStream pessoas;

    public Connection(Socket aClientSocket, PessoasOutputStream aPessoas) {
        try {
            clientSocket = aClientSocket;
            pessoas = aPessoas;
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

            String data = in.readUTF(); // read a line of data from the stream

            //separar os dados
            String[] dados = data.split("\n");
            String nome = dados[0];
            String cpf = dados[1];
            int idade = Integer.parseInt(dados[2]);

            // armazenar os dados em um objeto Pessoa
            Pessoa pessoa = new Pessoa(nome, cpf, idade);

            // adicionar o objeto Pessoa ao array de Pessoas
            pessoas.add(pessoa);

            // imprimir o array de Pessoas
            pessoas.showPeoples();

            // enviar o id da pessoa para o cliente
            out.writeUTF("id: " + pessoas.returnIdPessoa(pessoa));


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
