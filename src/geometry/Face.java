package geometry;

import java.awt.*;
import java.util.Arrays;

/**
 * A geometry.Face is a single polygon in a mesh. A geometry.Face consists of a color as well
 * as a set of vertices which define it.
 *
 * @author Marcello De Bernardi 26/09/2017.
 */
public class Face {
    public Point3D[] vertices;
    public Color color;
    public Point3D centroid;
    public Vector3D surfNormal;


    /**
     * Constructor for geometry.Face.
     *
     * @param vertices vertices associated with face
     * @param color    color of face
     */
    Face(Point3D[] vertices, Color color) {
        this.vertices = vertices;
        this.color = color;

        computeCentroid();
        computeFaceNormal();
    }


    /**
     * Calculates and sets the centroid of the face.
     */
    public void computeCentroid() {
        double xAvg = 0;
        double yAvg = 0;
        double zAvg = 0;

        for (Point3D p : vertices) {
            xAvg += p.x;
            yAvg += p.y;
            zAvg += p.z;
        }

        centroid = new Point3D(
                xAvg / vertices.length,
                yAvg / vertices.length,
                zAvg / vertices.length);
    }

    /**
     * Computes the face surface's normal vector based on two vectors that
     * define the plane of the face.
     */
    public void computeFaceNormal() {
        Vector3D v1 = Vector3D.vector(vertices[0], (vertices[1]));
        Vector3D v2 = Vector3D.vector(vertices[0], (vertices[2]));

        surfNormal = Vector3D.crossProduct(v1, v2).normalize();
    }

    public boolean isFrontFace(Vector3D viewplaneNormal) {
        return Vector3D.dotProduct(surfNormal, viewplaneNormal) > 0;
    }

    @Override
    public String toString() {
        return "Vertices: " + Arrays.asList(vertices) + "\nColor: " + color;
    }
}
