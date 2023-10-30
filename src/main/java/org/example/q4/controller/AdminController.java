package org.example.q4.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
            out.writeUTF("admin");
            String xmlCandidatos = candidatosToXml();
            out.writeUTF(xmlCandidatos);

            // TODO: criar socket multicast para enviar os avisos

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

            this.candidatos.add(new Candidato(nome, numero));

            scanner.nextLine();
            String novoCandidato = scanner.nextLine();
            if (novoCandidato.equals("n") || novoCandidato.equals("N")) {
                break;
            }
        }
    }

    private String candidatosToXml() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = xmlMapper.writeValueAsString(candidatos);
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao fazer o XML");
            return null;
        }
    }
}
