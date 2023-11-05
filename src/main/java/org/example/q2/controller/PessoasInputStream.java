package org.example.q2.controller;

import org.example.q1.model.Pessoa;
import org.example.q2.view.PessoasInputStreamView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class PessoasInputStream extends InputStream {
    private PessoasInputStreamView pessoasInputStreamView;
    private Pessoa[] pessoas;
    private final int SERVER_PORT = 7869;
    private final String IP = "localhost";

    public PessoasInputStream(Pessoa[] p, InputStream is) {
        this.pessoas = p;
        this.pessoasInputStreamView = new PessoasInputStreamView(is);
    }

    public Pessoa[] readTCP() {
        // cria um socket para ler e escrever dado
        Socket socketRead = null;

        try{
            // passa o endereço do servidor e a porta
            socketRead = new Socket(IP, SERVER_PORT);

            // cria um canal de saída para enviar os dados para o servidor
            DataOutputStream out = new DataOutputStream(socketRead.getOutputStream());
            DataInputStream in = new DataInputStream(socketRead.getInputStream());

            pessoas = pessoasInputStreamView.readDataPeople();
            int quantidade = pessoas.length;
            out.writeInt(quantidade);

            for (int i = 0; i < quantidade; i++) {
                // envia os dados para o servidor
                out.writeUTF(pessoas[i].getNome() + ';' + pessoas[i].getCpf() + ';' + pessoas[i].getIdade());

                // recebe o id da pessoa cadastrada
                String data = in.readUTF();

                System.out.println("Pessoa cadastrada com sucesso!");
                System.out.println("Seu id é: " + data);
            }
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

    public Pessoa[] readFile() {
        return null;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
