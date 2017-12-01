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
    private double movementSpeed = 1.0;
    /**
     * Constructs a new PerspectiveAnimator
     *
     * @throws FileNotFoundException if model files not found
     */
    public PerspectiveAnimator() throws FileNotFoundException {
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
        camera.setFrontClippingPlane(3);
        camera.setBackClippingPlane(-50);
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            // view modes
            case '1':
                scene.wireframe = false;
                break;
            case '2':
                scene.wireframe = true;
                break;
            case '3':
                scene.drawSurfaceNormals = !scene.drawSurfaceNormals;
                break;
            case '4':
                scene.drawVertexNormals = !scene.drawVertexNormals;
                break;
            // camera translations
            case 'w':
                ((PerspectiveCamera)camera).transform(new Matrix().setTranslation(0, 0, -movementSpeed));
                System.out.println(camera);
                break;
            case 'a':
                ((PerspectiveCamera)camera).transform(new Matrix().setTranslation(-movementSpeed, 0, 0));
                System.out.println(camera);
                break;
            case 'd':
                ((PerspectiveCamera)camera).transform(new Matrix().setTranslation(movementSpeed, 0, 0));
                System.out.println(camera);
                break;
            case 's':
                ((PerspectiveCamera)camera).transform(new Matrix().setTranslation(0, 0, movementSpeed));
                System.out.println(camera);
                break;
            // camera rotations
            // camera zoom
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}