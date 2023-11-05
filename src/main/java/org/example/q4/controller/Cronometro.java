package org.example.q4.controller;

import java.util.Date;

class Cronometro extends Thread {

    private Date dataFinal;
    private ServerController serverController;

    public Cronometro(Date dataFinal, ServerController serverController) {
        this.dataFinal = dataFinal;
        this.serverController = serverController;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            // Verifica se o Date chegou ao fim
            if (new Date().after(dataFinal)) {
                serverController.sendResult();
                break;
            }

            // Espera um segundo
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Erro no cronômetro");
            }
        }
    }
}
