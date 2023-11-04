package org.example.q4.view;

import org.example.q4.model.Candidato;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class VotanteView {
    private List<Candidato> candidatos;

    public VotanteView() {
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

    public String getLogin() {
        System.out.print("Digite seu título de eleitor: ");
        Scanner scanner = new Scanner(System.in);
        String tituloEleitor = scanner.nextLine();
        return tituloEleitor;
    }

    public void setCandidatos(List<Candidato> candidatos) {
        this.candidatos = candidatos;
    }
}
