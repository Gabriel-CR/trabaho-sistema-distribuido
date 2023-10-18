package org.example.q1.controller;

import org.example.q1.model.Pessoa;
import org.example.q1.view.PessoasOutputStreamView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class PessoasOutputStream extends OutputStream {
    private PessoasOutputStreamView pessoaView;
    private final int SERVER_PORT = 7869;
    private final String IP = "localhost";
    private Pessoa[] pessoas;

    public PessoasOutputStream(Pessoa[] pessoas, OutputStream outputStream) {
        this.pessoas = pessoas;
        this.pessoaView = new PessoasOutputStreamView(outputStream);
    }

    public void writeTCP() {
        // envia os dados de um conjunto (array) de Pessoas
        try{
            System.out.println("Aguardando conexão...");
            ServerSocket listenSocket = new ServerSocket(SERVER_PORT);

            while (true) {
                Socket clientSocket = listenSocket.accept();

                System.out.println("Conexão estabelecida com: "+clientSocket.getInetAddress().getHostAddress());
                Connection c = new Connection(clientSocket, this);
            }

        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        }
    }

    // adiciona um objeto Pessoa ao final do array de Pessoas
    public void add(Pessoa p) {
        if(pessoas[0] == null){
            pessoas[0] = p;
        }else{
            Pessoa[] novoArrayPessoas = new Pessoa[pessoas.length + 1];

            // Copiar os objetos existentes para o novo array
            for (int i = 0; i < pessoas.length; i++) {
                novoArrayPessoas[i] = pessoas[i];
            }

            // Adicionar o novo objeto ao final do novo array
            novoArrayPessoas[pessoas.length] = p;

            // Atualizar a referência para o novo array
            pessoas = novoArrayPessoas;
        }
    }

    public void showPeoples() {
        pessoaView.printArrayPessoas(pessoas);
    }

    public int returnIdPessoa(Pessoa pessoa) {
        for (int i = 0; i < pessoas.length; i++) {
            if (Objects.equals(pessoas[i].getCpf(), pessoa.getCpf())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void write(int b) throws IOException {
        // TODO
    }
}
