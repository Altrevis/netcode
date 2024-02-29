package com.serveur;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = "192.168.4.252"; // Remplacez cela par l'adresse IP de votre serveur
        int port = 8000;
        
        Socket socket = new Socket(serverAddress, port);
        System.out.println("Connecté au serveur");

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Lire les messages de l'utilisateur et les envoyer au serveur
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print("Client : ");
            userInput = scanner.nextLine();
            writer.println(userInput);

            // Quitter la boucle si l'utilisateur entre "exit"
            if (userInput.equals("exit")) {
                break;
            }

            // Afficher la réponse du serveur
            String response = reader.readLine();
            System.out.println("Réponse du serveur : " + response);
        }

        socket.close();
    }
}


/* 
try {
    Thread.sleep(10000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
*/