import graphics.Animator;
import graphics.PerspectiveAnimator;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * @author Marcello De Bernardi 01/12/2017.
 */
public class Main {
    private static Animator animator;
    private static JPanel settingsPane;
    private static JCheckBox surfaceNormals;
    private static JRadioButton wireframe;
    private static JRadioButton shaded;


    public static void main(String[] args) {
        System.out.println("---------------------------");
        System.out.println(" Marcello De Bernardi");
        System.out.println("---------------------------\n");
        System.out.println("LIST OF COMMANDS:");
        System.out.println("1: wireframe rendering");
        System.out.println("2: shaded rendering");
        System.out.println("3: toggle surface normals");
        System.out.println("4: toggle vertex normals");
        System.out.println("w,a,s,d: translate camera");

        try {
            animator = new PerspectiveAnimator();
            animator.start();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        /* settingsPane = new JPanel();
        drawSurfaceNormals = new JCheckBox();
        wireframe = new JRadioButton();
        shaded = new JRadioButton();

        JFrame testFrame = new JFrame();
        testFrame.setSize(1000, 700);

        settingsPane.add(drawSurfaceNormals);
        settingsPane.add(wireframe);
        settingsPane.add(shaded);
        testFrame.setContentPane(settingsPane);
        testFrame.setVisible(true); */
    }
}
