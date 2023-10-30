package org.example.q4.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.example.q4.model.Candidato;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerController {
    private Map<Integer, Candidato> candidatos = new HashMap<>();

    public ServerController() {
        candidatos.put(1, new Candidato("João", 1));
        candidatos.put(2, new Candidato("Maria", 2));
        candidatos.put(3, new Candidato("José", 3));
    }

    public void runServer() {
        try {
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            DatagramSocket ds = new DatagramSocket();

            String xml = candidatosToXml();

            byte[] b = xml.getBytes();
            DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 12347);
            System.out.println(xml);

            ds.send(pkg);
            ds.close();
            registerVote();
        } catch (Exception e) {
            System.out.println("Nao foi possivel enviar a mensagem");
        }
    }

    private void registerVote() {
        try{
            System.out.println("Aguardando conexão...");
            ServerSocket listenSocket = new ServerSocket(12348);

            while (true) {
                // TODO: zerar as pessoas antes de salvar no arquivo
                Socket clientSocket = listenSocket.accept();

                System.out.println("Conexão estabelecida com: "+clientSocket.getInetAddress().getHostAddress());
                Connection c = new Connection(clientSocket, this);
            }

        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        }
    }

    public String vote(int numeroCandidato) {
        var candidato = candidatos.get(numeroCandidato);

        if (candidato != null) {
            candidato.vote();
            return candidato.getNome();
        }
        return "falha";
    }

    public void showVotes() {
        for (Candidato candidato : candidatos.values()) {
            System.out.println(candidato + " " + candidato.getVotos());
        }
    }

    private String candidatosToXml() {
        List<Candidato> candidatosList = new ArrayList<>(candidatos.values());

        try {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = xmlMapper.writeValueAsString(candidatosList);
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao fazer o XML");
            return null;
        }
    }
}
