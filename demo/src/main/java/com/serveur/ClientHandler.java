package com.serveur;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket clientSocket; // Socket pour le client connecté
    private PrintWriter writer; // Pour envoyer des messages au client

    // Constructeur prenant le socket du client
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    // Méthode principale exécutée lorsqu'un nouveau thread est démarré
    public void run() {
        try {
            // Création des flux de lecture et d'écriture pour le client
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            // Boucle pour lire les messages du client en continu
            while ((message = reader.readLine()) != null) {
                System.out.println("Message du client : " + message);
                // Diffusion du message reçu à tous les autres clients connectés
                Server.broadcastMessage("Client " + clientSocket.getInetAddress() + " : " + message, this);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Affiche les erreurs d'entrée/sortie
        } finally {
            try {
                clientSocket.close(); // Ferme la connexion avec le client
            } catch (IOException e) {
                e.printStackTrace(); // Affiche les erreurs de fermeture de la connexion
            }
        }
    }

    // Méthode pour envoyer un message au client associé
    public void sendMessage(String message) {
        writer.println(message);
    }
}
