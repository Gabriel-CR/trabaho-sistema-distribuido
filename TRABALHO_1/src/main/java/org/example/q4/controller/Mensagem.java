package org.example.q4.controller;

public class Mensagem extends Thread {
    private ServerController serverController;

    public Mensagem(ServerController serverController) {
        this.serverController = serverController;
        this.start();
    }

    public void run() {
        while (true) {
            System.out.print("Digite a sua mensagem: ");
            serverController.sendMessage();
        }
    }
}
