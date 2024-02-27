package com.serveur;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Serveur démarré sur le port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connecté : " + clientSocket.getInetAddress());

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = reader.readLine();
            System.out.println("Message du client : " + message);

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            writer.println("Message reçu par le serveur : " + message);

            clientSocket.close();
        }
    }
}
