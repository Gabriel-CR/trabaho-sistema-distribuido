package org.example.q1.view;

import org.example.q1.model.Pessoa;

import java.io.OutputStream;
import java.io.PrintStream;

public class PessoasOutputStreamView {
    private OutputStream outputStream;

    public PessoasOutputStreamView(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void printArrayPessoas(Pessoa[] pessoas) {
        PrintStream opLocal = new PrintStream(outputStream);

        opLocal.println("Quantidade de pessoas: "+ pessoas.length);

        opLocal.println("---------------------------------");
        for (Pessoa pessoa : pessoas) {
            if (pessoa != null) {
                opLocal.println(pessoa);
                opLocal.println("---------------------------------");
            }
        }
    }
}
