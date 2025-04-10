package Projectiles;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Projectile {
    BufferedImage projectile;
    GamePanel gp;
    private int x, y;

    public int getX(){return x;}
    public int getY(){return y;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}

    public Projectile(GamePanel gp, int x, int y){
        this.gp = gp;
        this.x = x;
        this.y = y;
        try{
            this.projectile = ImageIO.read(getClass().getResourceAsStream("/Projectile/projectile1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        this.y -= 10;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(projectile, x, y, gp.getTilesize()/4, gp.getTilesize()/4, null);
    }
}
