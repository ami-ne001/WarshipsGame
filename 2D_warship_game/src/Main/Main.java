package Main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Créer une fenêtre JFrame
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Quitte l'application à la fermeture
        window.setResizable(false);  // La fenêtre ne peut pas être redimensionnée
        window.setTitle("Multiplayer Shooting Game");  // Titre de la fenêtre

        // Créer le panel du jeu et l'ajouter à la fenêtre
        GamePanel gamePanel = new GamePanel();  // Crée un objet GamePanel
        window.add(gamePanel);  // Ajoute le GamePanel à la fenêtre
        window.pack();  // Ajuste la taille de la fenêtre en fonction du GamePanel

        // Centrer la fenêtre au milieu de l'écran
        window.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        window.setVisible(true);

        // Démarrer le thread du jeu (boucle principale)
        gamePanel.startGameThread();  // Lance le thread de jeu
    }
}
