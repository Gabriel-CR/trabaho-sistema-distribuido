package org.example.q3.view;

import java.util.Scanner;

public class ClienteView {
    public ClienteView() {
    }

    public Double getValorForUser() throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o valor: ");
            Double valor = scanner.nextDouble();
            return valor;
        } catch (Exception exception) {
            throw new Exception("O valor deve ser um número");
        }
    }

    public String getBancoForUser() throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o banco: ");
            String banco = scanner.nextLine();
            return banco;
        } catch (Exception exception) {
            throw new Exception("Erro ao ler nome do banco");
        }
    }

    public String getAgenciaForUser() throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite a agencia: ");
            String agencia = scanner.nextLine();
            return agencia;
        } catch (Exception exception) {
            throw new Exception("Erro ao ler nome da agencia");
        }
    }
}
