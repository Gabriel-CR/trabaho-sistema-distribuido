package org.example.q2;

import org.example.q1.model.Pessoa;
import org.example.q2.controller.PessoasInputStream;

import java.io.IOException;

public class MainQ2 {
    public static void main(String[] args) throws IOException {
        Pessoa[] pessoas = new Pessoa[1];
        PessoasInputStream pis = new PessoasInputStream(pessoas, System.in);
        pessoas = pis.readTCP();
        pis.close();
    }
}
