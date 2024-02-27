package com.serveur;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = "10.34.6.218"; // Remplacez cela par l'adresse IP de votre ami
        int port = 8000;

        Socket socket = new Socket(serverAddress, port);
        System.out.println("Connecté au serveur");

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("hello"); // Modifiez le message à envoyer ici

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();
        System.out.println("Réponse du serveur : " + response);

        socket.close();
    }
}