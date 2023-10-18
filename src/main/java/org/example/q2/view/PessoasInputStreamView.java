package org.example.q2.view;

import org.example.q1.model.Pessoa;

import java.io.InputStream;
import java.util.Scanner;

public class PessoasInputStreamView {
    private InputStream inputStream;

    public PessoasInputStreamView(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Pessoa readDataPeople() {
        Scanner inScanner = new Scanner(inputStream);

        System.out.println("Informe seus dados...");
        System.out.print("Nome: ");
        String nome = inScanner.nextLine();
        System.out.print("CPF: ");
        String cpf = inScanner.nextLine();
        System.out.print("Idade: ");
        int idade = inScanner.nextInt();

        inScanner.close();

        return new Pessoa(nome, cpf, idade);
    }
}
