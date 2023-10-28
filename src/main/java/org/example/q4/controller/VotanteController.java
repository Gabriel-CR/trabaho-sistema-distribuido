package org.example.q4.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.q4.model.Candidato;
import org.example.q4.view.VotanteView;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

public class VotanteController {
    private VotanteView votanteView;
    private List<Candidato> candidatos;
    public VotanteController() {
        candidatos = new ArrayList<>();
        votanteView = new VotanteView(candidatos);
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

                String data = new String(pkg.getData());
                System.out.println("Dados recebidos: " + data);

                List<Candidato> candidatos = XMLToArray(data);
                System.out.println(candidatos.get(2));
                // TODO: votanteView está nulo mesmo após inicialização
                votanteView = new VotanteView(candidatos);
                System.out.println("oi");
                System.out.println(votanteView.getVoto());
                System.out.println("oi");
                mcs.close();
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
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

    private void showCandidatos() {
        for (Candidato candidato : candidatos) {
            System.out.println(candidato);
        }
    }
}
