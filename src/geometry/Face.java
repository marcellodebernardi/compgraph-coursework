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
    private int[] vertexIndices;
    private Color color;


    /**
     * Constructor for geometry.Face.
     *
     * @param vertexIndices array containing geometry.GObject indices of vertices
     * @param color         color of face
     */
    Face(int[] vertexIndices, Color color) {
        this.vertexIndices = vertexIndices;
        this.color = color;
    }


    /**
     * Returns an array containing the geometry.GObject indices of the vertices of the geometry.Face.
     */
    public int[] vertexIndices() {
        return vertexIndices;
    }

    /**
     * Returns the Color of the geometry.Face.
     *
     * @return face color
     */
    public Color color() {
        return color;
    }

    @Override
    public String toString() {
        return "Vertex indices: " + Arrays.asList(vertexIndices) + "\nColor: " + color;
    }
}
