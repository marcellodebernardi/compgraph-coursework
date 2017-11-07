package graphics;

import geometry.Point3D;
import geometry.Vector3D;

import java.io.FileNotFoundException;

/**
 * @author Marcello De Bernardi 01/10/2017.
 */
public class PerspectiveAnimator extends ParallelAnimator {
    public PerspectiveAnimator() throws FileNotFoundException {
        setupCamera();
    }

    public static void main(String[] args) {
        try {
            new PerspectiveAnimator().loop();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    protected void setupCamera() {
        camera = new PerspectiveCamera(-5, 5, -5, 5);
        ((PerspectiveCamera) camera).setupUVN(new Point3D(0, 0, 0), new Vector3D(0, 0, 1), new Vector3D(0, 1, 0));
        ((PerspectiveCamera) camera).setupCOP(new Point3D(0, 0, 3));
    }
}