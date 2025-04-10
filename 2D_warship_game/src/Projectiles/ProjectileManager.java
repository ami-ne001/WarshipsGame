package Projectiles;

import Entity.Player;
import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectileManager{
    GamePanel gp;
    KeyHandler keyh;
    Player player;
    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    public ProjectileManager(GamePanel gp, KeyHandler keyh, Player player){
        this.gp = gp;
        this.keyh = keyh;
        this.player = player;
    }

    public void update() {
        if (keyh.isShoot() && !keyh.isHeld()) {
            projectiles.add(new Projectile(gp, player.getx() + gp.getTilesize() / 3,
                    player.gety() - gp.getTilesize() / 3));
            keyh.setShoot(false);
        }

        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile p = iterator.next();
            p.update();
            if (p.getY() < -10) {
                iterator.remove();
            }
        }
    }

    public void draw(Graphics2D g2){
        for(Projectile p : projectiles){
            p.draw(g2);
        }
    }
}
