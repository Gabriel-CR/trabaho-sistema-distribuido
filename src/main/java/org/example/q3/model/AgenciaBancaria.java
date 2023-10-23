package org.example.q3.model;

import org.example.q1.model.Pessoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgenciaBancaria {
    private String nome;
    private Map<String, Conta> contas;

    public AgenciaBancaria(String nome) {
        this.nome = nome;
        this.contas = new HashMap<>() {{
            put("123", new Conta("123", "Maria", 10.0, 13.14));
            put("124", new Conta("124", "João", 0.0, 13.14));
        }};
    }

    public String getNome() {
        return nome;
    }

    public void adicionarConta(Conta conta) {
        contas.put(conta.getNumeroConta(), conta);
    }

    public Map<String, Conta> getContas() {
        return contas;
    }

    public void deposito(String contaId, Double valor) {
        this.contas.get(contaId).deposito(valor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Agência: ").append(nome).append('\n');

        sb.append("Contas:\n");
        for (Map.Entry<String, Conta> entry : contas.entrySet()) {
            String numeroConta = entry.getKey();
            Conta conta = entry.getValue();

            sb.append("\tNúmero da Conta: ").append(numeroConta).append(", ");
            sb.append("\tTitular: ").append(conta.getTitularConta()).append(", ");
            sb.append("\tSaldo: ").append(conta.getSaldo()).append(' ');
            sb.append('\n');
        }

        return sb.toString();
    }

}
