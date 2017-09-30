import java.awt.*;

/**
 * A Face is a single polygon in a mesh. A Face consists of a color as well as a set
 * of vertices which define it.
 *
 * @author Marcello De Bernardi 26/09/2017.
 */
public class Face {
    private int[] vertexIndices;
    private Color color;


    /**
     * Constructor for Face.
     *
     * @param vertexIndices array containing GObject indices of vertices
     * @param color         color of face
     */
    public Face(int[] vertexIndices, Color color) {
        this.vertexIndices = vertexIndices;
        this.color = color;
    }


    /**
     * Returns string representation of the face for debugging
     *
     * @return Face represented as String
     */
    public String toString() {
        /* Make it look nice to save your debugging time! */
        return null;
    }

    /**
     * Returns an array containing the GObject indices of the vertices of the Face.
     */
    int[] vertexIndices() {
        return vertexIndices;
    }

    /**
     * Returns the Color of the Face.
     *
     * @return face color
     */
    Color color() {
        return color;
    }
}
