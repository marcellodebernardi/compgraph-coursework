package graphics;

import geometry.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author Marcello De Bernardi 01/10/2017.
 * <p>
 * A Scene consists of an array of GObjects
 */
class Scene {
    private GObject[] objects;
    private Point3D lightSource;        // location of light in world coordinates
    private double sourceIntensity;     // intensity of light source
    private double ambientIntensity;    // intensity of ambient light

    public boolean drawSurfaceNormals;
    public boolean drawVertexNormals;
    public boolean wireframe;


    Scene(String[] fileName) throws FileNotFoundException {
        objects = new GObject[fileName.length];

        for (int i = 0; i < fileName.length; i++) {
            objects[i] = new GObject(fileName[i]);
        }

        lightSource = new Point3D(3, 3, 3);
        sourceIntensity = 1;
        ambientIntensity = 0.1;
    }


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
     *
     * @param cam camera
     * @param gfx graphics
     */
    void draw(Camera cam, Graphics gfx) {
        // todo z buffering for hidden surface occlusion
        // todo refactor to make more legible
        for (GObject object : objects) {
            for (Face face : object.faces()) {

                if (drawSurfaceNormals) drawSurfaceNormals(gfx, cam, face);

                // back face removal
                if (isBackFace(face, cam)) continue;

                /* if (clip(cam, faceVertices[0], faceVertices[1], faceVertices[2]))
                    continue; */

                Point3D[] pixelPoints = cam.project(face.vertices);

                int[] xCoordinates = new int[pixelPoints.length];
                int[] yCoordinates = new int[pixelPoints.length];

                for (int i = 0; i < pixelPoints.length; i++) {
                    xCoordinates[i] = (int) pixelPoints[i].x;
                    yCoordinates[i] = (int) pixelPoints[i].y;
                }

                if (wireframe) {
                    gfx.setColor(flatShade(face));
                    gfx.fillPolygon(xCoordinates, yCoordinates, pixelPoints.length);
                }
                else {
                    gfx.setColor(face.color);
                    gfx.drawPolygon(xCoordinates, yCoordinates, pixelPoints.length);
                }

                if (drawVertexNormals) continue;
            }
        }
    }

    private boolean isBackFace(Face face, Camera cam) {
        Vector3D viewVector = cam instanceof PerspectiveCamera ?
                Vector3D.vector(face.centroid, ((PerspectiveCamera)cam).cop)
                : cam.getViewPlaneNormal();

        return Vector3D.dotProduct(face.surfNormal, viewVector) < 0;
    }

    private boolean clip(Camera cam, Point3D... points) {
        // todo proper clipping
        // returns true if any of the points are beyond clipping planes
        for (Point3D p : points) {
            if (p.z < cam.getBackClippingPlane() || p.z > cam.getFrontClippingPlane())
                return true;
        }
        return false;
    }

    // calculates the color
    private Color flatShade(Face face) {
        Vector3D lightDirection = Vector3D.vector(face.centroid, lightSource);
        double cosTheta = ((Vector3D.dotProduct(lightDirection, face.surfNormal)
                / (Vector3D.L2norm(lightDirection) * Vector3D.L2norm(face.surfNormal))) + 1) / 2;

        // todo fix out of bound values
        int r = (int) (face.color.getRed() * cosTheta * sourceIntensity + ambientIntensity);
        int g = (int) (face.color.getGreen() * cosTheta * sourceIntensity + ambientIntensity);
        int b = (int) (face.color.getBlue() * cosTheta * sourceIntensity + ambientIntensity);

        return new Color(r, g, b);
    }

    private void drawFace(Graphics gfx, Camera cam, Face face) {

    }

    private void drawSurfaceNormals(Graphics gfx, Camera cam, Face face) {
        Point3D centroid = Point3D.copy(face.centroid);
        Point3D normalEnd = Point3D.copy(centroid);
        normalEnd.transform(new Matrix().setTranslation(face.surfNormal.x, face.surfNormal.y, face.surfNormal.z));

        centroid = cam.project(centroid)[0];
        normalEnd = cam.project(normalEnd)[0];

        gfx.setColor(Color.WHITE);
        gfx.drawLine(
                (int) centroid.x,
                (int) centroid.y,
                (int) normalEnd.x,
                (int) normalEnd.y);
    }

    @Override
    public String toString() {/* Make it look nice to save your debugging time! */
        return "GObjects: \n" + Arrays.asList(objects);
    }
}
