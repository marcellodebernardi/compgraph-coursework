package geometry;

import graphics.Camera;
import graphics.PerspectiveCamera;

import java.util.Arrays;

/**
 * @author Marcello De Bernardi 01/12/2017.
 * <p>
 * A data structure that abstracts an array of faces, sortable
 * by distance to camera.
 */
public class FaceArray {
    private Face[] faces;
    private boolean[] display;

    public FaceArray(GObject[] objects) {
        int number = 0;
        for (GObject obj : objects) number += obj.faces().length;

        faces = new Face[number];
        display = new boolean[number];

        int i = 0;
        for (GObject obj : objects) {
            for (Face f : obj.faces()) {
                faces[i] = f;
                i++;
            }
        }
    }

    public void reset() {
        for (int i = 0; i < display.length; i++) display[i] = true;
    }

    @SuppressWarnings("ComparatorCombinators")
    public void sort(Camera cam) {
        if (cam instanceof PerspectiveCamera) {
            Point3D cop = ((PerspectiveCamera) cam).cop;

            Arrays.sort(faces, (a, b) ->
                    Double.compare(Point3D.distance(b.centroid, cop), Point3D.distance(a.centroid, cop)));
        }
        else {
            Arrays.sort(faces, (a, b)
                    -> Double.compare(b.centroid.z, a.centroid.z));
        }
    }

    public int size() {
        return faces.length;
    }

    public Face get(int index) {
        return faces[index];
    }

    public boolean display(int index) {
        return display[index];
    }

    public void elim(int index) {
        display[index] = false;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i <= faces.length; i++)
            result += faces[i].toString() + ", ";
        return result;
    }

}
