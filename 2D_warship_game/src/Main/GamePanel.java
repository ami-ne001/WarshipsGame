package Main;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Background.Background;
import Entity.Player;
import Projectiles.ProjectileManager;
import Chat.ChatClient;

public class GamePanel extends JPanel implements Runnable {

    private final int tilesize = 48;
    private final int maxScreenCol = 28;
    private final int maxScreenRow = 15;
    private final int screenWidth = tilesize * maxScreenCol;
    private final int screenHeight = tilesize * maxScreenRow;

    int FPS = 144;

    Background bg = new Background(this);
    KeyHandler keyh = new KeyHandler(this);
    Thread gameThread;
    Player player = new Player(this, this.keyh);
    ProjectileManager projectiles = new ProjectileManager(this, keyh, player);

    ChatClient chatClient;
    JTextField chatInput;
    List<String> chatMessages = new ArrayList<>();
    boolean typing = false;

    public int getScreenWidth() {
        return this.screenWidth;
    }
    public int getScreenHeight() {
        return this.screenHeight;
    }
    public int getFPS() {
        return this.FPS;
    }
    public int getTilesize(){return this.tilesize;}

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);

        setupChat();
        startChatReaderThread();
    }

    public void setupChat() {
        chatInput = new JTextField();
        chatInput.setBounds(10, screenHeight - 40, 300, 30);
        chatInput.setVisible(false);

        chatInput.addActionListener(e -> {
            String msg = chatInput.getText();
            if (!msg.isEmpty()) {
                chatClient.sendMessage("[Player] " + msg);
                chatInput.setText("");
            }
        });

        this.setLayout(null);
        this.add(chatInput);

        chatClient = new ChatClient("localhost", 12345);
    }

    private void startChatReaderThread() {
        new Thread(() -> {
            try {
                BufferedReader reader = chatClient.getInputReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    chatMessages.add(line);
                    if (chatMessages.size() > 10)
                        chatMessages.remove(0);
                    repaint();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void toggleTypingMode() {
        typing = !typing;
        chatInput.setVisible(typing);
        if (typing) chatInput.requestFocus();
        else this.requestFocusInWindow();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        if (!typing) {
            player.update();
            projectiles.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        bg.draw(g2);
        player.draw(g2);
        projectiles.draw(g2);

        g2.setColor(Color.WHITE);
        int y = screenHeight - 60;
        for (String msg : chatMessages) {
            g2.drawString(msg, 10, y);
            y -= 15;
        }

        g2.dispose();
    }

    @Override
    public void run(){
        double updateinterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currenttime;

        while(gameThread!=null){
            currenttime = System.nanoTime();
            delta += (currenttime - lastTime) / updateinterval;
            lastTime = currenttime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }
}
