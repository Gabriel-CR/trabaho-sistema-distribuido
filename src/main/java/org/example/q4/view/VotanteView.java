package org.example.q4.view;

import org.example.q4.model.Candidato;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class VotanteView {
    private List<Candidato> candidatos;

    public VotanteView(List<Candidato> candidatos) {
        this.candidatos = candidatos;
    }

    public int getVoto() {
        int voto = getNumeroVoto();

        for (Candidato c : candidatos) {
            if (c.getNumero() == voto) {
                return voto;
            }
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
        for (Candidato candidato : candidatos) {
            System.out.println(candidato);
        }
    }
}
