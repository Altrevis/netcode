package com.serveur;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static List<ClientHandler> clients = new ArrayList<>(); // Liste des clients connectés

    // Méthode principale
    public static void main(String[] args) throws IOException {
        int port = 8000;
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(port); // Création du serveur sur le port spécifié
        System.out.println("Serveur démarré sur le port " + port);

        // Boucle pour accepter les connexions des clients en continu
        while (true) {
            Socket clientSocket = serverSocket.accept(); // Accepte la connexion d'un client
            System.out.println("Client connecté : " + clientSocket.getInetAddress());

            // Création d'un nouveau thread pour gérer le client connecté
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clients.add(clientHandler); // Ajoute le gestionnaire de client à la liste
            clientHandler.start(); // Démarre le thread
        }
    }

    // Méthode pour diffuser un message à tous les clients sauf l'expéditeur
    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) { // Vérifie si le client n'est pas l'expéditeur
                client.sendMessage(message); // Envoie le message au client
            }
        }
    }
}
