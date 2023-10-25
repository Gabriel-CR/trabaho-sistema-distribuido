package org.example.q3.model;

import java.util.HashMap;
import java.util.Map;

public class Banco {
    private String nome;
    private Map<String, AgenciaBancaria> agencias;

    public Banco(String nome) {
        this.nome = nome;
        this.agencias = new HashMap<>() {{
            put("1", new AgenciaBancaria("1"));
            put("2", new AgenciaBancaria("2"));
            put("3", new AgenciaBancaria("3"));
        }};
    }

    public String deposito(String agenciaId, String contaId, Double valor) {
        var agencia = this.agencias.get(agenciaId);

        if (agencia != null) {
            return agencia.deposito(contaId, valor);
        } else {
            return "Agencia " + agenciaId + " não encontrada";
        }
    }

    public void adicionarAgencia(AgenciaBancaria agencia) {
        agencias.put(agencia.getNome(), agencia);
    }

    public Map<String, AgenciaBancaria> getAgencias() {
        return agencias;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Banco: ").append(nome).append('\n');

        sb.append("Agências:\n");
        for (Map.Entry<String, AgenciaBancaria> entry : agencias.entrySet()) {
            AgenciaBancaria agencia = entry.getValue();

            sb.append(agencia.toString()); // Chama o método toString da classe AgenciaBancaria
        }

        return sb.toString();
    }

}
