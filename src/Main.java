import graphics.PerspectiveAnimator;

import java.io.FileNotFoundException;

/**
 * @author Marcello De Bernardi 01/12/2017.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------------");
        System.out.println(" Marcello De Bernardi");
        System.out.println("---------------------------\n");
        System.out.println("LIST OF COMMANDS:");
        System.out.println("1: toggle wireframe/shaded rendering");
        System.out.println("2: toggle surface normals");
        System.out.println("0: increase animation speed");
        System.out.println("9: decrease animation speed");
        System.out.println("w,a,s,d: translate camera");
        System.out.println("q: zoom out ");
        System.out.println("e: zoom in");

        try {
            new PerspectiveAnimator().start();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
