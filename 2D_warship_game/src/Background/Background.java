package Background;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background{
    private BufferedImage background;
    private GamePanel gp;

    public Background(GamePanel gp){
        try{
            this.background = ImageIO.read(getClass().getResourceAsStream("/Background/backgroundTile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.gp = gp;
    }

    public BufferedImage getBackground() {
        return background;
    }
    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(background, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
    }
}
