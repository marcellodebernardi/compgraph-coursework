package graphics;

import geometry.Face;
import geometry.GObject;
import geometry.Matrix;
import geometry.Point3D;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author Marcello De Bernardi 01/10/2017.
 *
 * A Scene consists of an array of GObjects
 */
class Scene {
    private GObject[] objects;


    Scene(String[] fileName) throws FileNotFoundException {
        objects = new GObject[fileName.length];

        for (int i = 0; i < fileName.length; i++) {
            objects[i] = new GObject(fileName[i]);
        }
    }


    // todo needs an abstraction for complex scene transformations

    /**
     * Transforms the entire scene with a given 4D transformation matrix.
     * The transformation is applied to all objects in the scene.
     *
     * @param matrix the 4x4 transformation matrix
     */
    void transform(Matrix matrix) {
        for (GObject object : objects) object.transform(matrix);
    }

    /**
     * Draws polygons from the Scene onto the Graphics context using the
     * given Camera.
     * @param cam
     * @param gfx
     */
    void draw(Camera cam, Graphics gfx){
        for (GObject object : objects) {
            for (Face face : object.faces()) {
                Point3D[] faceVertices = object.vertices(face);

                if (!Point3D.isFrontFace(
                        faceVertices[0],
                        faceVertices[1],
                        faceVertices[2],
                        cam.getViewPlaneNormal()))
                    continue;
                // todo clipping

                Point3D[] pixelPoints = cam.project(faceVertices);

                int[] xCoordinates = new int[pixelPoints.length];
                int[] yCoordinates = new int[pixelPoints.length];

                for (int i = 0; i < pixelPoints.length; i++) {
                    xCoordinates[i] = (int) pixelPoints[i].x();
                    yCoordinates[i] = (int) pixelPoints[i].y();
                }

                gfx.setColor(face.color());
                gfx.fillPolygon(xCoordinates, yCoordinates, pixelPoints.length);
            }
        }
    }

    @Override
    public String toString() {/* Make it look nice to save your debugging time! */
        return "GObjects: \n" + Arrays.asList(objects);
    }
}
