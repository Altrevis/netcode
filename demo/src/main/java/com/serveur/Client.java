package com.serveur;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = "10.34.6.218"; 
        int port = 8000;
        
        Socket socket = new Socket(serverAddress, port);
        System.out.println("Connecté au serveur");

        final PrintWriter[] writer = new PrintWriter[1];
        writer[0] = new PrintWriter(socket.getOutputStream(), true);
        final BufferedReader[] reader = new BufferedReader[1];
        reader[0] = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        final Scanner[] scanner = new Scanner[1];
        scanner[0] = new Scanner(System.in);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response;
                    while ((response = reader[0].readLine()) != null) {
                        System.out.println("Réponse du serveur : " + response);
                        System.out.print("Client : ");
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
                while (true) {
                    System.out.print("Client : ");
                    userInput = scanner[0].nextLine();
                    writer[0].println(userInput);

                    if (userInput.equals("exit")) {
                        break;
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

        socket.close();
    }
}
