package geometry;

import graphics.Camera;
import graphics.PerspectiveCamera;

import java.util.Arrays;

/**
 * @author Marcello De Bernardi 01/12/2017.
 * <p>
 * A data structure that abstracts an array of faces and supports operations
 * useful in back face elimination and depth sorting.
 */
public class FaceArray {
    // parallel arrays, "display" specifies whether each face should be displayed
    private Face[] faces;
    private boolean[] display;


    /**
     * Creates a new FaceArray with references to all the Face objects belonging
     * to the GObjects passed as arguments.
     *
     * @param objects objects from which to extract faces
     */
    public FaceArray(GObject ... objects) {
        int number = 0;
        for (GObject obj : objects)
            number += obj.faces.length;

        faces = new Face[number];
        display = new boolean[number];

        int i = 0;
        for (GObject obj : objects) {
            for (Face f : obj.faces) {
                faces[i] = f;
                i++;
            }
        }
    }

    /**
     * Resets the display status of all faces to true.
     */
    public void reset() {
        for (int i = 0; i < display.length; i++) display[i] = true;
    }

    /**
     * Sorts the array of faces in accordance to distance from the viewer. The
     * viewer is defined by the Camera passed as argument.
     *
     * @param cam viewer camera
     */
    @SuppressWarnings("ComparatorCombinators")
    public void sort(Camera cam) {
        // for perspective projection
        if (cam instanceof PerspectiveCamera) {
            Point3D cop = ((PerspectiveCamera) cam).cop;

            Arrays.sort(faces, (a, b) ->
                    Double.compare(Point3D.distance(b.centroid, cop), Point3D.distance(a.centroid, cop)));
        }
        // for parallel projection
        else {
            Arrays.sort(faces, (a, b)
                    -> Double.compare(b.centroid.z, a.centroid.z));
        }
    }

    /**
     * Returns the size of the FaceArray
     *
     * @return number of faces
     */
    public int size() {
        return faces.length;
    }

    /**
     * Returns the Face at the given index.
     *
     * @param index index into array
     * @return face at index
     */
    public Face get(int index) {
        return faces[index];
    }

    /**
     * Returns whether the face at index should be displayed or not.
     *
     * @param index face index
     * @return true if to be displayed, false otherwise
     */
    public boolean display(int index) {
        return display[index];
    }

    /**
     * Specifies that the face at index is not to be displayed.
     *
     * @param index index of face not to display
     */
    public void elim(int index) {
        display[index] = false;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < faces.length; i++)
            result += faces[i].toString() + ", ";
        return result;
    }

}
