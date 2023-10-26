package org.example.q1;

import org.example.q1.controller.PessoasOutputStream;
import org.example.q1.model.Pessoa;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainFileQ1 {
    public static void main(String[] args) throws IOException {
        Pessoa[] pessoas = new Pessoa[1];
        FileOutputStream outputStream = new FileOutputStream("./saida.txt");
        PessoasOutputStream pos = new PessoasOutputStream(pessoas, outputStream);
        pos.writeTCP();
        pos.close();
    }
}
