package org.example.q4.controller;

import org.example.q4.model.Candidato;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminController {
    private final String IP = "localhost";
    private final int SERVER_PORT = 7896;
    private List<Candidato> candidatos;

    public AdminController() {
        candidatos = new ArrayList<>();
    }

    public void runAdmin() {
        Socket s = null;
        try {
            s = new Socket(IP, SERVER_PORT);

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            // TODO: pegar candidatos
            registerCandidatos();

            // TODO: enviar candidatos

            // TODO: criar socket multicast para enviar os avisos

            String envio = "sistemas_distribuidos";
            System.out.println("Sent: "+envio);
            out.writeUTF(envio); // UTF is a string encoding see Sn. 4.4
            String data = in.readUTF(); // read a line of data from the stream
            System.out.println("Received: " + data);
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
        }
    }

    private void registerCandidatos() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("====== REGISTRAR CANDIDATO ======");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Número: ");
            int numero = scanner.nextInt();
        }
    }
}
