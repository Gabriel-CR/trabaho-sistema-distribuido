package org.example.q2.controller;

import org.example.q1.model.Pessoa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class PessoasInputStream extends InputStream {
    private InputStream is;
    private Pessoa[] pessoas;
    private final int SERVER_PORT = 7869;
    private final String IP = "localhost";

    public PessoasInputStream(Pessoa[] p, InputStream is) {
        this.pessoas = p;
        this.is = is;
    }

    public Pessoa[] readTCP() {
        // cria um socket para ler e escrever dado
        Socket socketRead = null;
        // cria um scanner para ler os dados do terminal
        Scanner inScanner = new Scanner(is);

        try{
            // passa o endereço do servidor e a porta
            socketRead = new Socket("localhost", 7896);

            // cria um canal de saída para enviar os dados para o servidor
            DataOutputStream out = new DataOutputStream(socketRead.getOutputStream());
            DataInputStream in = new DataInputStream(socketRead.getInputStream());

            // recebe os dados do terminal
            System.out.println("Informe seus dados...");
            System.out.print("Nome: ");
            String nome = inScanner.nextLine();
            System.out.print("CPF: ");
            String cpf = inScanner.nextLine();
            System.out.print("Idade: ");
            int idade = inScanner.nextInt();

            inScanner.close();

            // envia os dados para o servidor
            pessoas = new Pessoa[1];
            pessoas[0] = new Pessoa(nome, cpf, idade);
            out.writeUTF(pessoas[0].getNome() + "\n" + pessoas[0].getCpf() + "\n" + pessoas[0].getIdade());

            // recebe o id da pessoa cadastrada
            String data = in.readUTF();

            System.out.println("Pessoa cadastrada com sucesso!");
            System.out.println("Seu id é: " + data);

        }catch(IOException e){
            System.out.println("Socket:" + e.getMessage());

        }finally{
            if(socketRead != null){
                try{
                    socketRead.close();
                }catch(IOException e){
                    System.out.println("close:" + e.getMessage());
                }
            }
        }

        return pessoas;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
