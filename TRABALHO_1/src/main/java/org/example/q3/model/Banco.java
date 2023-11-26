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

    public String saque(String agenciaId, String contaId, Double valor) {
        var agencia = this.agencias.get(agenciaId);

        if (agencia != null) {
            return agencia.saque(contaId, valor);
        } else {
            return "Agencia " + agenciaId + " não encontrada";
        }
    }

    public String saldo(String agenciaId, String contaId) {
        var agencia = this.agencias.get(agenciaId);

        if (agencia != null) {
            return agencia.saldo(contaId);
        } else {
            return "Agencia " + agenciaId + " não encontrada";
        }
    }

    public String taxaJuros(String agenciaId, String contaId) {
        var agencia = this.agencias.get(agenciaId);

        if (agencia != null) {
            return agencia.taxaJuros(contaId);
        } else {
            return "Agencia " + agenciaId + " não encontrada";
        }
    }

    public String calcularJuros(String agenciaId, String contaId) {
        var agencia = this.agencias.get(agenciaId);

        if (agencia != null) {
            return agencia.calcularJuros(contaId);
        } else {
            return "Agencia " + agenciaId + " não encontrada";
        }
    }

    public String transferir(String agenciaId, String contaId, Double valor, String agenciaIdDestino, String contaIdDestino) {
        var agencia = this.agencias.get(agenciaId);
        var agenciaDestino = this.agencias.get(agenciaIdDestino);

        if (agencia != null && agenciaDestino != null) {
            agencia.saque(contaId, valor);
            agenciaDestino.deposito(contaIdDestino, valor);
            return "Transferência de R$" + valor + " realizada com sucesso";
        }else if(agenciaDestino != null){
            return "Agencia " + agenciaId + " não encontrada";
        } else {
            return "Agencia " + agenciaId + " não encontrada";
        }
    }

    public void adicionarAgencia(AgenciaBancaria agencia) {
        agencias.put(agencia.getNome(), agencia);
    }

    public String adicionarConta(String tipo, String agenciaId, String contaId, String nome, Double saldo) {
        var agencia = this.agencias.get(agenciaId);
        var conta = new Conta(contaId, nome, saldo, 0.0);
        
        if (tipo.equals("1")) {
            conta = new ContaCorrente(contaId, nome, saldo , 0.0);
        } else if (tipo.equals("2")) {
            conta = new ContaPoupancao(contaId, nome, saldo, 13.43);
        } else {
            return "Tipo de conta não encontrado";
        }

        if (agencia != null && agencia.getContas().containsKey(contaId) == false) {
            agencia.adicionarConta(conta);
            System.out.println("Conta adicionada com sucesso");

            return "Conta adicionada com sucesso";
        } else {
            return "Agencia " + agenciaId + "não encontrada";
        }
    }


    public String encerrarConta(String agenciaId, String contaId){
        var agencia = this.agencias.get(agenciaId);

        if(agencia != null && agencia.getContas().containsKey(contaId) == true){
            agencia.encerrarConta(contaId);
            System.out.println("Conta removida com sucesso");

            return "Conta removida com sucesso";
        }else{
            return "Agencia " + agenciaId + "não encontrada ou conta " + contaId + " não encontrada";
        }
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
