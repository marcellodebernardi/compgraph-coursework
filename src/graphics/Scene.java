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
    private FaceArray faces;
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

        faces = new FaceArray(objects);

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
        faces.reset();
        faces.sort(cam);

        // back face elimination and clipping
        for (int i = 0; i < faces.size(); i++)
            if ((!wireframe) && (isBackFace(faces.get(i), cam) || clip(faces.get(i), cam)))
                faces.elim(i);

        // draw surface normals of back faces
        if (drawSurfaceNormals)
            for (int i = 0; i < faces.size(); i++)
                if (!faces.display(i)) drawSurfaceNormal(gfx, cam, faces.get(i));

        // draw front faces and their surface normals
        for (int i = 0; i < faces.size(); i++) {
            if (!faces.display(i)) continue;

            Face face = faces.get(i);
            Point3D[] pixelPoints = cam.project(face.vertices);

            int[] xCoordinates = new int[pixelPoints.length];
            int[] yCoordinates = new int[pixelPoints.length];

            for (int j = 0; j < pixelPoints.length; j++) {
                xCoordinates[j] = (int) pixelPoints[j].x;
                yCoordinates[j] = (int) pixelPoints[j].y;
            }

            if (!wireframe) {
                gfx.setColor(flatShade(face));
                gfx.fillPolygon(xCoordinates, yCoordinates, pixelPoints.length);
            }
            else {
                gfx.setColor(face.color);
                gfx.drawPolygon(xCoordinates, yCoordinates, pixelPoints.length);
            }

            if (drawSurfaceNormals) drawSurfaceNormal(gfx, cam, face);

            if (drawVertexNormals) continue;
        }
    }


    /* Checks if the face given as argument is a back face, as seen from the
    camera passed as the second argument. */
    private boolean isBackFace(Face face, Camera cam) {
        Vector3D viewVector = cam instanceof PerspectiveCamera ?
                Vector3D.vector(face.centroid, ((PerspectiveCamera)cam).cop)
                : cam.getViewPlaneNormal();

        return Vector3D.dotProduct(face.surfNormal, viewVector) < 0;
    }

    /* Clips faces where at least one vertex is behind the camera. Better
    results would be given by subdividing the faces. */
    private boolean clip(Face face, Camera cam) {
        for (Point3D p : face.vertices) {
            if (p.z < cam.bcp || p.z > cam.fcp)
                return true;
        }
        return false;
    }

    private void drawFace(Graphics gfx, Camera cam, Face face) {

    }

    /* Draws onto the screen the surface normal of the given face */
    private void drawSurfaceNormal(Graphics gfx, Camera cam, Face face) {
        Point3D centroid = face.centroid.clone();
        Point3D normalEnd = centroid.clone();
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

    /* Applies shading the face passed as argument, by considering an ambient light
    as well as a light source. */
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

    @Override
    public String toString() {/* Make it look nice to save your debugging time! */
        return "GObjects: \n" + Arrays.asList(objects);
    }
}
