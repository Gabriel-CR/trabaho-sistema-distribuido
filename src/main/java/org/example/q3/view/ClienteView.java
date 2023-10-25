package org.example.q3.view;

import java.util.Scanner;

public class ClienteView {
    public ClienteView() {
    }

    /*
     * Menu de opções que o código faz
     * Dispara um Exception caso o usuário digite um valor incorreto
     *      Ex: o valor deve ser um número e o usuário digita uma letra
     */
    public String getOpcaoMenu() throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ESCOLHA UMA OPÇÃO");
            System.out.println("[1] deposito\n" +
                    "[2] saque\n" +
                    "[3] saldo\n" +
                    "[4] verificar taxa de juros\n" +
                    "[5] calcular juros\n" +
                    "[6] transferir\n" +
                    "[7] encerrar conta\n" +
                    "[q] sair");
            System.out.print("Sua escolha: ");
            String opcao = scanner.nextLine();
            return opcao;
        } catch (Exception exception) {
            throw new Exception("Erro ao ler opção de operação");
        }
    }

    /*
     * Recebe o valor em dinheiro do usuário
     * Dispara uma Exception caso o usuário digite um valor fora do esperado
     */
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

    public String getContaForUser() throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite a conta: ");
            String banco = scanner.nextLine();
            return banco;
        } catch (Exception exception) {
            throw new Exception("Erro ao ler nome da conta");
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
