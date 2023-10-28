package org.example.q4.view;

import org.example.q4.model.Candidato;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VotanteView {
    private HashMap<Integer ,Candidato> candidatos;

    public VotanteView(List<Candidato> candidatos) {
//        this.candidatos = new HashMap<Integer, Candidato>();
        for (Candidato c : candidatos) {
            System.out.println(c);
            candidatos.add(c.getNumero(), c);
        }
    }

    public int getVoto() {
        int voto = getNumeroVoto();

        var candidato = candidatos.get(voto);

        if (candidato != null) {
            return voto;
        }
        return -1;
    }

    private int getNumeroVoto() {
        showCandidatos();
        System.out.print("Escolha seu voto: ");
        Scanner scanner = new Scanner(System.in);
        int voto = scanner.nextInt();
        return voto;
    }

    private void showCandidatos() {
        for (Candidato candidato : candidatos.values()) {
            System.out.println(candidato);
        }
    }
}
