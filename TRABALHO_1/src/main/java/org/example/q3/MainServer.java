package org.example.q3;

import org.example.q3.controller.ServerController;

public class MainServer {
    public static void main(String[] args) {
        ServerController serverController = new ServerController();
        serverController.serverRun();
    }
}
