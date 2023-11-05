package org.example.q4.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.q4.model.Candidato;
import org.example.q4.view.VotanteView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotanteController {
    private VotanteView votanteView;
    private Map<Integer, Candidato> candidatos = new HashMap<>();

    public VotanteController() {
        this.votanteView = new VotanteView();
    }

    public void runVotante() {
        while (true) {
            try {
                System.out.println("Cliente esperando multicast");
                MulticastSocket mcs = new MulticastSocket(12347);
                InetAddress grp = InetAddress.getByName("239.0.0.1");
                mcs.joinGroup(grp);

                byte rec[] = new byte[256];
                DatagramPacket pkg = new DatagramPacket(rec, rec.length);
                mcs.receive(pkg);

                if (login()) {
                    String data = new String(pkg.getData());
                    System.out.println("Dados recebidos: " + data);

                    List<Candidato> candidatos = XMLToArray(data);
                    votanteView.setCandidatos(candidatos);

                    sendVote();
                    mcs.close();
                    break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        esperarResultado();
    }

    private void esperarResultado() {
        try {
            System.out.println("Cliente esperando resultado da eleição");
            MulticastSocket mcs = new MulticastSocket(12347);
            InetAddress grp = InetAddress.getByName("239.0.0.1");
            mcs.joinGroup(grp);

            byte rec[] = new byte[256];
            DatagramPacket pkg = new DatagramPacket(rec, rec.length);
            mcs.receive(pkg);

            String data = new String(pkg.getData());
            System.out.println("Resultado: " + data);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private boolean login() {
        // cria um socket para ler e escrever dado
        Socket socketRead = null;

        try {
            // passa o endereço do servidor e a porta
            InetAddress grp = InetAddress.getByName("localhost");
            socketRead = new Socket(grp, 12348);

            // cria um canal de saída para enviar os dados para o servidor
            DataOutputStream out = new DataOutputStream(socketRead.getOutputStream());
            DataInputStream in = new DataInputStream(socketRead.getInputStream());

            // informando o tipo de client para o servidor
            out.writeUTF("votantelogin");
            String login = votanteView.getLogin();
            out.writeUTF(login);
            String response = in.readUTF();
            System.out.println(response);
            if (response.equals("Falha ao fazer login")) {
                return false;
            }
            return true;
        } catch(IOException e) {
            System.out.println("Socket:" + e.getMessage());
        } finally {
            if (socketRead != null) {
                try {
                    socketRead.close();
                } catch(IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
        return false;
    }

    private void sendVote() {
        // cria um socket para ler e escrever dado
        Socket socketRead = null;

        try {
            // passa o endereço do servidor e a porta
            // TODO: trocal o localhost para o IP do servidor quando for rodar o cliente
            InetAddress grp = InetAddress.getByName("localhost");
            socketRead = new Socket(grp, 12348);

            // cria um canal de saída para enviar os dados para o servidor
            DataOutputStream out = new DataOutputStream(socketRead.getOutputStream());
            DataInputStream in = new DataInputStream(socketRead.getInputStream());

            // informando o tipo de client para o servidor
            out.writeUTF("votante");

            int voto = votanteView.getVoto();
            out.writeInt(voto);
            String response = in.readUTF();
            System.out.println(response);
        } catch(IOException e) {
            System.out.println("Socket:" + e.getMessage());
        } finally {
            if (socketRead != null) {
                try {
                    socketRead.close();
                } catch(IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }

    private List<Candidato> XMLToArray(String xml) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            List<Candidato> candidatos = xmlMapper.readValue(xml, new TypeReference<List<Candidato>>(){});
            return candidatos;
        } catch (Exception e) {
            System.out.println("Erro ao transformar XML para List<Candidato>");
            return null;
        }
    }

}
