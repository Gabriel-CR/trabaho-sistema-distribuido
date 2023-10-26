package org.example.q2;

import org.example.q1.model.Pessoa;
import org.example.q2.controller.PessoasInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFileQ2 {
    public static void main(String[] args) throws IOException {
        Pessoa[] pessoas = new Pessoa[1];
        FileInputStream inputStream = new FileInputStream("./entrada.txt");
        PessoasInputStream pis = new PessoasInputStream(pessoas, inputStream);
        pessoas = pis.readTCP();
        pis.close();
    }
}
