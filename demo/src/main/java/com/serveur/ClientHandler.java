package com.serveur;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter writer;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Message du client : " + message);

                // Condition pour gérer les demandes de fichiers
                if (message.equals("request_file")) {
                    try {
                        // Génération du contenu du fichier texte
                        String fileContent = "Contenu du fichier généré par le serveur.";
                        
                        // Envoi du contenu du fichier au client ligne par ligne
                        writer.println(fileContent);

                        // Affichage côté serveur
                        System.out.println("Fichier envoyé au client : ");
                        System.out.println(fileContent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Diffusion du message à tous les clients connectés
                    Server.broadcastMessage("Client " + clientSocket.getInetAddress() + " : " + message, this);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                Server.clientDisconnected(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void sendFile(File file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendFile'");
    }
}
