package org.example.q3.controller;

import org.example.q3.view.ClienteView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClienteController {
    private ClienteView clienteView;

    public ClienteController() {
        clienteView = new ClienteView();
    }

    public void deposito() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("deposito");
            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getBancoForUser();
            Double valor = clienteView.getValorForUser();

            out.writeUTF(agencia);
            out.writeUTF(conta);
            out.writeDouble(valor);
            // TODO: verificar em caso de arro, receber mensagem de erro do servidor
            System.out.println("Depósito de R$" + valor + " realizado com sucesso");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void dados() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("dados");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
