package graphics;

import geometry.Matrix;
import geometry.Point3D;
import geometry.Vector3D;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

/**
 * @author Marcello De Bernardi 01/10/2017.
 * <p>
 * A perspective animator differs from a ParallelAnimator only in its use of
 * a PerspectiveCamera.
 */
public class PerspectiveAnimator extends ParallelAnimator {
    /**
     * Constructs a new PerspectiveAnimator
     *
     * @throws FileNotFoundException if model files not found
     */
    PerspectiveAnimator() throws FileNotFoundException {
        setupCamera();
        this.addKeyListener(this);
    }


    /**
     * Application entry point
     * @param args unused
     */
    public static void main(String[] args) {
        try {
            new PerspectiveAnimator().loop();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    /**
     * Sets up a PerspectiveCamera.
     */
    protected void setupCamera() {
        camera = new PerspectiveCamera(-5, 5, -5, 5);
        ((PerspectiveCamera) camera).setupUVN(new Point3D(0, 0, 0), new Vector3D(0, 0, 1), new Vector3D(0, 1, 0));
        ((PerspectiveCamera) camera).setCOP(new Point3D(0, 0, 3));
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            // camera translations
            case 'w':
                ((PerspectiveCamera)camera).translateCOP(new Matrix().setTranslation(0, 0, -1));
                break;
            case 'a':
                ((PerspectiveCamera)camera).translateCOP(new Matrix().setTranslation(-1, 0, 0));
                break;
            case 'd':
                ((PerspectiveCamera)camera).translateCOP(new Matrix().setTranslation(1, 0, 0));
                break;
            case 's':
                ((PerspectiveCamera)camera).translateCOP(new Matrix().setTranslation(0, 0, 1));
                break;
            // camera rotations
            // camera zoom
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}