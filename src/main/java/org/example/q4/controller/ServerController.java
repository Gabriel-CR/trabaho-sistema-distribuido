package org.example.q4.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.q4.model.Candidato;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private List<Candidato> candidatos;

    public ServerController() {
        candidatos = new ArrayList<>();

        candidatos.add(new Candidato("João", 1));
        candidatos.add(new Candidato("Maria", 2));
        candidatos.add(new Candidato("José", 3));
    }

    public void runServer() {
        try {
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            DatagramSocket ds = new DatagramSocket();

//            byte[] b = "Ola Mundo SD 2022.2 de novo".getBytes();
//            DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 12347);

            String xml = candidatosToXml();

            byte[] b = xml.getBytes();
            DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 12347);
            System.out.println(xml);

            ds.send(pkg);
            ds.close();

        } catch (Exception e) {
            System.out.println("Nao foi possivel enviar a mensagem");
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
