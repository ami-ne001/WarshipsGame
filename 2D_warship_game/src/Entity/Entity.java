package Entity;

import java.awt.image.BufferedImage;

public abstract class Entity{
    protected int x, y;
    protected int hp;
    protected int attack;
    protected int vitesse;

    protected BufferedImage ship;

    public int getHp(){return hp;}
    public int getAttack(){return attack;}
    public int getVitesse(){return vitesse;}

    public void setHp(int hp){this.hp = hp;}
    public void setAttack(int attack){this.attack = attack;}
    public void setVitesse(int vitesse){this.vitesse = vitesse;}
}
