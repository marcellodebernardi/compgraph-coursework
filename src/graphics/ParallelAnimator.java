package graphics;

/**
 * @author Marcello De Bernardi 01/10/2017.
 */

import geometry.Matrix;

import java.awt.*;
import java.io.FileNotFoundException;

import static java.lang.Math.PI;

public class ParallelAnimator extends Animator {
    protected Camera camera;
    private String[] files = {"./models/cube.dat", "./models/pyramid.dat"};
    private Scene scene;
    private boolean surfaceNormals = false;
    private boolean vertexNormals = false;
    private boolean wireframe = true;


    ParallelAnimator() throws FileNotFoundException {
        super();

        scene = new Scene(files);

        System.out.println(scene);

        setupCamera();
    }

    public static void main(String[] args) {
        try {
            new ParallelAnimator().loop();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    protected void setupCamera() {
        // todo is this really necessary?
        camera = new Camera(-5, 5, -5, 5);
    }


    protected void animate(Graphics g) {
        camera.setViewport(getWidth(), getHeight());

        if (g == null || scene == null || camera == null) return;

        Matrix mX = new Matrix(), mY = new Matrix(), mZ = new Matrix();
        mX.setRotationX(-PI / 400);
        mY.setRotationY(PI / 400);
        mZ.setRotationZ(PI / 400);
        scene.transform(mZ.multiply(mY.multiply(mX)));


        scene.draw(camera, g);
    }
}
