package org.example.q4;

import org.example.q4.controller.ServerController;

public class MainServer {
    public static void main(String[] args) {
        ServerController serverController = new ServerController();

        serverController.runServer();
    }
}
