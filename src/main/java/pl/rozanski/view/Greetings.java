package pl.rozanski.view;

import pl.rozanski.enums.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static pl.rozanski.pong.GameInstance.GAME_INSTANCE;


public class Greetings {
    private BufferedImage rainbow = null;
    private Image buff = null;
    private Color c = new Color(255, 255, 255);
    private Color cStroke = new Color(255, 255, 254);
    private Color bg = new Color(0, 0, 0);
    private Font titleFont = new Font("Arial Black", Font.BOLD, 160);
    private Font subTitleFont = new Font("Arial Black", Font.BOLD, 70);
    private Surface surface;
    private double degrees = 0;

    public static boolean needsUpdate = true;

    public Greetings(Surface surface) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("graphics/rainbow.jpg");
            rainbow = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.surface = surface;

    }

    public void initGreetings(String greets) {

        BufferedImage buff = new BufferedImage(surface.getWidth(), surface.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gbi = buff.createGraphics();
        gbi.setBackground(bg);


        gbi.setFont(titleFont);
        float width = gbi.getFontMetrics().stringWidth(greets);
        float height = gbi.getFontMetrics().getHeight();
        gbi.setPaint(cStroke);
        gbi.drawString(greets, (int) ((buff.getWidth() - width) / 2) - 1, (int) ((buff.getHeight() + height / 2) / 2) - 1);
        gbi.drawString(greets, (int) ((buff.getWidth() - width) / 2) + 1, (int) ((buff.getHeight() + height / 2) / 2) + 1);
        gbi.drawString(greets, (int) ((buff.getWidth() - width) / 2) - 1, (int) ((buff.getHeight() + height / 2) / 2) + 1);
        gbi.drawString(greets, (int) ((buff.getWidth() - width) / 2) + 1, (int) ((buff.getHeight() + height / 2) / 2) - 1);

        gbi.setPaint(c);
        gbi.drawString(greets, (int) ((buff.getWidth() - width) / 2), (int) ((buff.getHeight() + height / 2) / 2));

        if (GAME_INSTANCE.getGameState() == GameState.GAME_OVER) {
            gameOverSubTitleGreets(buff, gbi);
        }

        gbi.dispose();

        this.buff = Transparency.makeColorTransparent(buff, c);

    }

    private void gameOverSubTitleGreets(BufferedImage buff, Graphics2D gbi) {
        float width;
        float height;
        String tmp;
        if (GAME_INSTANCE.getScore()[0] > 3) {
            tmp = "LEFT WON";
        } else {
            tmp = "RIGHT WON";
        }
        gbi.setFont(subTitleFont);
        width = gbi.getFontMetrics().stringWidth(tmp);
        height = gbi.getFontMetrics().getHeight();

        gbi.setPaint(cStroke);
        gbi.drawString(tmp, (int) ((buff.getWidth() - width) / 2) - 1, (int) ((buff.getHeight() + height / 2) / 2 + 100) - 1);
        gbi.drawString(tmp, (int) ((buff.getWidth() - width) / 2) - 1, (int) ((buff.getHeight() + height / 2) / 2 + 100) + 1);
        gbi.drawString(tmp, (int) ((buff.getWidth() - width) / 2) + 1, (int) ((buff.getHeight() + height / 2) / 2 + 100) - 1);
        gbi.drawString(tmp, (int) ((buff.getWidth() - width) / 2) + 1, (int) ((buff.getHeight() + height / 2) / 2 + 100) + 1);

        gbi.setPaint(c);
        gbi.drawString(tmp, (int) ((buff.getWidth() - width) / 2), (int) ((buff.getHeight() + height / 2) / 2 + 100));
    }


    public void renderGreetings(Graphics2D g2d, String greets) {

        double locationX = rainbow.getWidth() / 2;
        double locationY = rainbow.getHeight() / 2;
        degrees += 1.5;
        degrees %= 360;

        double rotationRequired = Math.toRadians(degrees);
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        if (needsUpdate) {
            initGreetings(greets);
            needsUpdate = false;
        }
        g2d.drawImage(rainbow, op, (surface.getWidth() - rainbow.getWidth()) / 2, (surface.getHeight() - rainbow.getHeight() / 2) / 4);
        g2d.drawImage(this.buff, 0, 0, null);

    }

}
