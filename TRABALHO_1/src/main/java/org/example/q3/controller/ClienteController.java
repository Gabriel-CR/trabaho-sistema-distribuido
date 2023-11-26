package org.example.q3.controller;

import org.example.q3.view.ClienteView;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClienteController {
    private ClienteView clienteView;
    private final String IP = "localhost";
    private final int PORT = 9000;

    public ClienteController() {
        clienteView = new ClienteView();
    }

    public void runClient() {
        while (true) {
            try {
                String opcao = clienteView.getOpcaoMenu();

                if (opcao.equals("1")) { // deposito
                    deposito();
                } else if (opcao.equals("2")) { // saque
                    saque();
                } else if (opcao.equals("3")) { // saldo
                    saldo();
                } else if (opcao.equals("4")) { // taxa de juros
                    taxaJuros();
                } else if (opcao.equals("5")) { // calcular juros
                    calcularJuros();
                } else if (opcao.equals("6")) { // transferir
                    transferir();
                }else if (opcao.equals("7")) { // abrir conta
                    adicionarConta();
                }else if (opcao.equals("8")) { // encerrar conta
                    encerrarConta();
                } else if (opcao.equals("q")) { // sair
                    System.out.println("Sistema encerrado");
                    return;
                } else {
                    System.out.println("Opção invalida, escolha uma opção vãlida");
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao ler opção");
            }
        }
    }

    /*
     * Faz o depósito de um valor em uma conta
     * Usa a view para receber os valores
     * Faz um único envio de dados para o servidor e recebe uma resposta
     *      Envia para o servidor uma string separada por ';' com todos os dados necessários para a operação
     */
    public void deposito() {
        Socket socket = null;
        try {
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getContaForUser();
            Double valor = clienteView.getValorForUser();
            /*
             * Envia dados para uma operação no servidor
             * O primeiro argumento é a operação a ser feita
             * Os outros argumentos são os dados necessários para a operação
             */
            out.writeUTF("deposito;" + agencia + ";" + conta + ";" + valor);

            System.out.println(in.readUTF());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar socket");
        }
    }

    /*
     * Faz o saque de um valor em uma conta
     * Usa a view para receber os valores
     */
    public void saque() {
        Socket socket = null;
        try {
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getContaForUser();
            Double valor = clienteView.getValorForUser();
            out.writeUTF("saque;" + agencia + ";" + conta + ";" + valor);

            System.out.println(in.readUTF());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar socket");
        }
    }

    /*
     * Verifica o saldo de uma conta
     * Usa a view para receber os valores
     */
    public void saldo() {
        Socket socket = null;
        try {
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getContaForUser();
            out.writeUTF("saldo;" + agencia + ";" + conta);

            System.out.println(in.readUTF());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar socket");
        }
    }

    /*
     * Verifica a taxa de juros de uma conta
     * Usa a view para receber os valores
     */
    public void taxaJuros(){
        Socket socket = null;

        try{
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getContaForUser();

            out.writeUTF("taxaJuros;" + agencia + ";" + conta);

            System.out.println(in.readUTF());
        }catch(Exception e){
            throw new RuntimeException("Erro ao criar socket");
        }
    }


    public void calcularJuros(){
        Socket socket = null;

        try{
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getContaForUser();

            out.writeUTF("calcularJuros;" + agencia + ";" + conta);

            System.out.println(in.readUTF());
        }catch(Exception e){
            throw new RuntimeException("Erro ao criar socket");
        }
    }


    /*
     * Faz a transferência de um valor de uma conta para outra
     * Usa a view para receber os valores
     */
    public void transferir() {
        Socket socket = null;
        try {
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getContaForUser();
            Double valor = clienteView.getValorForUser();
            String agenciaDestino = clienteView.getAgenciaForUser();
            String contaDestino = clienteView.getContaForUser();
            out.writeUTF("transferir;" + agencia + ";" + conta + ";" + valor + ";" + agenciaDestino + ";" + contaDestino);

            System.out.println(in.readUTF());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar socket");
        }
    }


    /// debug
    public void dados() {
        Socket socket = null;
        try {
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("dados");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar socket");
        }
    }

    /*
     * Faz a adição de uma conta no mapa de contas
     * Usa a view para receber os valores
     */
    public void adicionarConta(){
        Socket socket = null;

        try{
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            
            String tipo = clienteView.getTipoContaForUser();
            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getContaForUser();
            String nome = clienteView.getNomeForUser();
            String saldo = clienteView.getSaldoForUser();

            out.writeUTF("adicionarConta;" + tipo + ";" + agencia + ";" + conta + ";" + nome + ";" + saldo);
            System.out.println(in.readUTF());
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar socket");
        }
    }


    /*
     * Faz a remoção de uma conta no mapa de contas
     * Usa a view para receber os valores
     */
    public void encerrarConta(){
        Socket socket = null;

        try{
            socket = new Socket(IP, PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String agencia = clienteView.getAgenciaForUser();
            String conta = clienteView.getContaForUser();

            out.writeUTF("encerrarConta;" + agencia + ";" + conta);

            System.out.println(in.readUTF());


        }catch(Exception e){
            throw new RuntimeException("Erro ao criar socket");
        }
    }
}
