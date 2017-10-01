import java.awt.*;
import java.util.Scanner;

/**
 * A GObject is a 3D mesh consisting of a set of vertices and faces.
 *
 * @author Marcello De Bernardi 26/09/2017.
 */
public class GObject {
    private Point3D[] vertices;
    private Face[] faces;


    /**
     * Constructor for GObject that uses directly passed Point3D and Face arrays.
     *
     * @param vertices
     * @param faces
     */
    GObject(Point3D[] vertices, Face[] faces) {
        this.vertices = vertices;
        this.faces = faces;
    }

    /**
     * Constructor for GObject that reads in Point3D vertices and Face objects from a file.
     *
     * @param fileName path of data file for 3D object
     */
    GObject(String fileName) {
        // todo break up into helper methods?
        // todo could make a helper method for the repeating if (hasNext) checks

        Scanner scanner = new Scanner(fileName);
        int vertexNumber = 0;
        int faceNumber = 0;

        // check for empty file
        if (!scanner.hasNext()) {
            System.err.println(this + ": failed to construct due to empty data file");
            return;
        }


        // get number of vertices
        if (!scanner.hasNextInt()) {
            System.err.println(this + ": failed to construct due to missing number of vertices");
            return;
        }
        vertexNumber = scanner.nextInt();
        vertices = new Point3D[vertexNumber];

        // get vertices
        for (int i = 0; i < vertexNumber; i++) {
            double[] coordinates = new double[3];

            for (int j = 0; j < 3; j++) {
                if (!scanner.hasNextDouble()) {
                    System.err.println(this + ": failed to construct due to missing vertex coordinate");
                    return;
                }
                coordinates[j] = scanner.nextDouble();
            }
            vertices[i] = new Point3D(coordinates[0], coordinates[1], coordinates[2]);
        }


        // get number of faces
        if (!scanner.hasNextInt()) {
            System.err.println(this + ": failed to construct due to missing number of faces");
            return;
        }
        faceNumber = scanner.nextInt();

        // get faces
        for (int i = 0; i < faceNumber; i++) {
            int[] faceVertices;

            if (!scanner.hasNextInt()) {
                System.err.println(this + ": failed to construct due to missing number of face vertices");
                return;
            }
            faceVertices = new int[scanner.nextInt()];

            for (int j = 0; j < faceVertices.length; j++) {
                if (!scanner.hasNextInt()) {
                    System.err.println(this + ": failed to construct due to missing vertex index");
                    return;
                }
                faceVertices[j] = scanner.nextInt();
            }

            if (!scanner.hasNextInt()) {
                System.err.println(this + ": failed to construct due to missing face color");
                return;
            }
            int color = scanner.nextInt();

            faces[i] = new Face(faceVertices, new Color(color));
        }
    }


    /**
     * Returns the array of vertices of this GObject.
     *
     * @return vertex array
     */
    Point3D[] vertices() {
        return vertices;
    }

    /**
     * Returns the array of faces of this GObject.
     *
     * @return face array
     */
    Face[] faces() {
        return faces;
    }

    /**
     * Transforms the GObject by the matrix given as argument
     * @param matrix
     */
    void transform(Matrix matrix) {

    }

    /**
     * Returns a string representation of the object.
     *
     * @return
     */
    public String toString() {
        /* Make it look nice to save your debugging time! */
        return null;
    }
}
