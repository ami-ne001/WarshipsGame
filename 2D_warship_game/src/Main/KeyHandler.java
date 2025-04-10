package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    private GamePanel gp;
    private boolean up, down, left, right;
    private boolean shoot, held;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    public boolean isUp() {
        return up;
    }
    public boolean isDown() {
        return down;
    }
    public boolean isLeft() {
        return left;
    }
    public boolean isRight() {
        return right;
    }
    public boolean isShoot() {
        return shoot;
    }
    public boolean isHeld() {
        return held;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }
    public void setHeld(boolean held) {
        this.held = held;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER) {
            gp.toggleTypingMode();
        }

        if (!gp.typing) {
            if(code == KeyEvent.VK_UP) up = true;
            if(code == KeyEvent.VK_DOWN) down = true;
            if(code == KeyEvent.VK_RIGHT) right = true;
            if(code == KeyEvent.VK_LEFT) left = true;
            if(code == KeyEvent.VK_SPACE) {
                shoot = true;
                held = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP) up = false;
        if(code == KeyEvent.VK_DOWN) down = false;
        if(code == KeyEvent.VK_RIGHT) right = false;
        if(code == KeyEvent.VK_LEFT) left = false;
        if(code == KeyEvent.VK_SPACE) held = false;
    }
}
