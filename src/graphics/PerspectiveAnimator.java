package graphics;

import geometry.Vector3D;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import static java.awt.event.KeyEvent.*;

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
     *
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
        camera.fcp = ((PerspectiveCamera) camera).cop.z;
        camera.bcp = camera.fcp - 50;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // view modes
            case VK_1:
                scene.wireframe = !scene.wireframe;
                break;
            case VK_2:
                scene.drawSurfaceNormals = !scene.drawSurfaceNormals;
                break;
            // camera translations
            case VK_W:
                ((PerspectiveCamera) camera).translate(new Vector3D(0, 0, -movementSpeed));
                System.out.println(camera);
                break;
            case VK_A:
                ((PerspectiveCamera) camera).translate(new Vector3D(-movementSpeed, 0, 0));
                System.out.println(camera);
                break;
            case VK_D:
                ((PerspectiveCamera) camera).translate(new Vector3D(movementSpeed, 0, 0));
                System.out.println(camera);
                break;
            case VK_S:
                ((PerspectiveCamera) camera).translate(new Vector3D(0, 0, movementSpeed));
                System.out.println(camera);
                break;
            // zoom out
            case VK_Q:
                if (camera.currentZoom == 0) break;
                camera.yMin--;
                camera.yMax++;
                camera.xMin--;
                camera.xMax++;
                camera.currentZoom--;
                break;
            // zoom in
            case VK_E:
                if (camera.currentZoom == camera.maxZoom) break;
                camera.yMin++;
                camera.yMax--;
                camera.xMin++;
                camera.xMax--;
                camera.currentZoom++;
                break;
            // animation speed up
            case VK_0:
                animationSpeed += 0.01;
                break;
            // animation speed down
            case VK_9:
                animationSpeed -= 0.01;
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}