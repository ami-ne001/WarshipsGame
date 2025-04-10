package Chat;

import Main.GamePanel;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static final int PORT = 12345;
    private static final Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();  // Accepte une connexion client
                new Thread(new ClientHandler(clientSocket)).start();  // Démarre un thread pour chaque client
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Gère la connexion de chaque client
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Ouvre les flux d'entrée et de sortie pour communiquer avec le client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.add(out);  // Ajoute le client à la liste des clients
                }

                String message;
                // Lit les messages du client et les envoie à tous les autres clients
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);  // Affiche le message reçu
                    synchronized (clientWriters) {
                        // Envoie le message à tous les clients connectés
                        for (PrintWriter writer : clientWriters) {
                            writer.println(message);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                // Ferme les ressources lorsque le client se déconnecte
                try {
                    if (out != null) {
                        synchronized (clientWriters) {
                            clientWriters.remove(out);  // Supprime le client de la liste
                        }
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    socket.close();  // Ferme la connexion avec le client
                } catch (IOException e) {
                    System.err.println("Error closing resources: " + e.getMessage());
                }
            }
        }
    }
}
