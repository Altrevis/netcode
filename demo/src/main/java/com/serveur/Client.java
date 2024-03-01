package com.serveur;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // Adresse IP du serveur
        int port = 8000; // Port du serveur

        // Connexion au serveur
        Socket socket = new Socket(serverAddress, port);
        System.out.println("Connecté au serveur");

        // Initialisation des flux de lecture et d'écriture
        final PrintWriter[] writer = new PrintWriter[1];
        writer[0] = new PrintWriter(socket.getOutputStream(), true);
        final BufferedReader[] reader = new BufferedReader[1];
        reader[0] = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        final Scanner[] scanner = new Scanner[1];
        scanner[0] = new Scanner(System.in);

        // Thread pour lire les réponses du serveur
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            String response;
            // Boucle pour lire les réponses du serveur en continu
            while ((response = reader[0].readLine()) != null) {
                System.out.println("Réponse du serveur : " + response);
                System.out.print("Client : "); // Invite pour le client à entrer un message
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}).start();

new Thread(new Runnable() {
    @Override
    public void run() {
        String userInput;
        boolean sendMessages = false;
        // Boucle pour lire les entrées de l'utilisateur en continu
        while (true) {
            if (sendMessages) {
                System.out.print("Client : "); // Invite pour l'utilisateur à entrer un message
                userInput = scanner[0].nextLine(); // Lire l'entrée de l'utilisateur
                if (userInput.equalsIgnoreCase("exitnow")) {
                    sendMessages = false;
                } else {
                    writer[0].println(userInput); // Envoyer le message au serveur
                }
            } else {
                System.out.println("1. Envoyer un message");
                System.out.println("2. Demander un fichier");
                System.out.print("Sélectionnez une option : ");
                userInput = scanner[0].nextLine(); // Lire l'entrée de l'utilisateur
                if (userInput.equals("1")) {
                    sendMessages = true;
                } else if (userInput.equals("2")) {
                    writer[0].println("request_file"); // Envoyer une demande de fichier au serveur
                }
            }
        }
    }
}).start();


        // Attendre que les threads se terminent
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Fermer la connexion
        socket.close();
    }
}
