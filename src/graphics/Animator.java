package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * @author Marcello De Bernardi 26/09/2017.
 */
public class Animator extends JFrame implements KeyListener {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int INTERVAL = 10;
    private int R;
    private BufferedImage image;


    Animator() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(WIDTH, HEIGHT);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    /**
     * Application entry point for 2D animation
     * @param args cli arguments
     */
    public static void main(String[] args) {
        new Animator().loop();
    }

    public final void paintComponent(Graphics g) {
    }

    public void start() {
        loop();
    }

    /**
     * Main graphics loop to be inherited and used by all subclasses of Animator,
     * defining the core functionality common to all Animators.
     */
    protected final void loop() {
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();

        // rendering settings
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        while (true) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(0, 0, getWidth(), getHeight());

            animate(g2);

            ((Graphics2D) getGraphics()).drawImage(image, 0, 0, null);
            paintComponent(getGraphics());
            try {
                Thread.sleep(INTERVAL);
            }
            catch (InterruptedException e) {
                // continue
            }
        }
    }

    // draws polygons on Graphics context, private because each animator has its own
    protected void animate(Graphics g) {
        g.setColor(Color.RED);
        R = R > 150 ? 0 : R + 2;
        g.fillPolygon(new int[]{500, 600, 600, 500}, new int[]{500, 500, 600, 600 + 3 * R}, 4);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // unused
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // unused
    }
}