package com.serveur;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // Remplacez cela par l'adresse IP de votre serveur
        int port = 8000;

        Socket socket = new Socket(serverAddress, port);
        System.out.println("Connecté au serveur");

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("hello"); // Modifiez le message à envoyer ici

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();
        System.out.println("Réponse du serveur : " + response);

        // Pause de 10 secondes avant de fermer la connexion
        try {
            Thread.sleep(10000); // 10 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        socket.close();
    }
}
