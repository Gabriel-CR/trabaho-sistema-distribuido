package org.example.q1;

import org.example.q1.controller.PessoasOutputStream;
import org.example.q1.model.Pessoa;

import java.io.IOException;

public class MainQ1 {
    public static void main(String[] args) throws IOException {
        Pessoa[] pessoas = new Pessoa[1];
        // TODO: adaptar para saida por arquivo
        PessoasOutputStream pos = new PessoasOutputStream(pessoas, System.out);
        pos.writeTCP();
        pos.close();
    }
}
