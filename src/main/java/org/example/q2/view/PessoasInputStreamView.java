package org.example.q2.view;

import org.example.q1.model.Pessoa;

import java.io.InputStream;
import java.util.Scanner;

public class PessoasInputStreamView {
    private InputStream inputStream;

    public PessoasInputStreamView(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int readQuantityPeople() {
        Scanner inScanner = new Scanner(inputStream);

        // ler a quantidade de pessoas
        int quantidadeDePessoas = inScanner.nextInt();

        inScanner.close();
        return quantidadeDePessoas;
    }

    public Pessoa[] readDataPeople() {
        Scanner inScanner = new Scanner(inputStream);
        Pessoa[] pessoas;

        // ler a quantidade de pessoas
        int quantidadeDePessoas = inScanner.nextInt();
        pessoas = new Pessoa[quantidadeDePessoas];

        inScanner.nextLine(); // Consuma a quebra de linha após a leitura do número de pessoas.

        for (int i = 0; i < quantidadeDePessoas; i++) {
            System.out.println("Informe seus dados...");
            System.out.print("Nome: ");
            String nome = inScanner.nextLine();
            System.out.print("CPF: ");
            String cpf = inScanner.nextLine();
            System.out.print("Idade: ");
            int idade = inScanner.nextInt();

            if (i < quantidadeDePessoas - 1) {
                inScanner.nextLine(); // Consuma a quebra de linha após a leitura da idade.
            }

            pessoas[i] = new Pessoa(nome, cpf, idade);
        }

        inScanner.close();

        return pessoas;
    }

    public Pessoa readDataPeopleFile(String filePath) {
        // TODO
        return null;
    }
}
