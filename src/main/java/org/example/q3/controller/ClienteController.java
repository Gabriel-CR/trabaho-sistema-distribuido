package org.example.q3.controller;

import org.example.q3.view.ClienteView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClienteController {
    private ClienteView clienteView;
    private final String IP = "localhost";
    private final int PORT = 9000;

    public ClienteController() {
        clienteView = new ClienteView();
    }

    public void runClient() {
        while (true) {
            try {
                String opcao = clienteView.getOpcaoMenu();

                if (opcao.equals("1")) { // deposito
                    deposito();
                } else if (opcao.equals("2")) { // saque
                    saldo();
                } else if (opcao.equals("3")) { // saldo
                    System.out.println("TODO");
                } else if (opcao.equals("4")) { // taxa de juros
                    System.out.println("TODO");
                } else if (opcao.equals("5")) { // calcular juros
                    System.out.println("TODO");
                } else if (opcao.equals("6")) { // transferir
                    System.out.println("TODO");
                } else if (opcao.equals("7")) { // encerrar conta
                    System.out.println("TODO");
                } else if (opcao.equals("q")) { // sair
                    System.out.println("Sistema encerrado");
                    return;
                } else {
                    System.out.println("Opção invalida, escolha uma opção vãlida");
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao ler opção");
            }
        }
    }

    public void deposito() {
        Socket socket = null;
        try {
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getContaForUser();
            Double valor = clienteView.getValorForUser();
            out.writeUTF("deposito;" + agencia + ";" + conta + ";" + valor);

            System.out.println(in.readUTF());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saldo() {
        Socket socket = null;
        try {
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("oi");
            if (in.readUTF() == null) {
                System.out.println("Depósito de R$" + 10 + " realizado com sucesso");
            }  else {
                System.out.println("Erro ao selecionar opcao");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void dados() {
        Socket socket = null;
        try {
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("dados");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
