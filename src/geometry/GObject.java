package geometry;


import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A geometry.GObject is a 3D mesh consisting of a set of vertices and faces.
 *
 * @author Marcello De Bernardi 26/09/2017.
 */
public class GObject {
    private Point3D[] vertices;
    private Face[] faces;


    /**
     * Constructor for geometry.GObject that uses directly passed
     * geometry.Point3D and geometry.Face arrays.
     *
     * @param vertices array of points representing vertices of GObject
     * @param faces    faces of the object
     */
    public GObject(Point3D[] vertices, Face[] faces) {
        this.vertices = vertices;
        this.faces = faces;
    }

    /**
     * Constructor for geometry.GObject that reads in geometry.Point3D vertices
     * and geometry.Face objects from a file.
     *
     * @param fileName path of data file for 3D object
     */
    public GObject(String fileName) throws FileNotFoundException {
        // todo break up into helper methods?
        // todo could make a helper method for the repeating if (hasNext) checks

        Scanner scanner = new Scanner(new File(fileName));

        vertices = new Point3D[0];
        faces = new Face[0];

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
        faces = new Face[faceNumber];

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

            if (!scanner.hasNextFloat()) {
                System.err.println(this + ": failed to construct due to missing face color");
                return;
            }
            float rColor = scanner.nextFloat();
            float gColor = scanner.nextFloat();
            float bColor = scanner.nextFloat();

            faces[i] = new Face(faceVertices, new Color(rColor, gColor, bColor));
        }
    }


    /**
     * Returns the array of vertices of this geometry.GObject.
     *
     * @return vertex array
     */
    public Point3D[] vertices() {
        return vertices;
    }

    /**
     * Returns an array of vertices with all vertices of the given Face.
     * The method does not check whether the face belongs to the GObject,
     * so ArrayIndexOutOfBoundExceptions may be thrown.
     *
     * @param
     * @return
     */
    public Point3D[] vertices(Face face) throws ArrayIndexOutOfBoundsException {
        int[] vertexIndices = face.vertexIndices();
        Point3D[] faceVertices = new Point3D[vertexIndices.length];

        for (int i = 0; i < faceVertices.length; i++)
            faceVertices[i] = vertices[vertexIndices[i]];

        return faceVertices;
    }

    /**
     * Returns the array of faces of this geometry.GObject.
     *
     * @return face array
     */
    public Face[] faces() {
        return faces;
    }

    /**
     * Transforms the geometry.GObject by the matrix given as argument
     *
     * @param matrix transformation matrix for all vertices
     */
    public void transform(Matrix matrix) {
        for (int i = 0; i < vertices.length; i++)
            vertices[i] = vertices[i].transform(matrix);
    }

    @Override
    public String toString() {
        return "Vertices: " + Arrays.asList(vertices) + "\nFaces: " + Arrays.asList(faces);
    }
}
