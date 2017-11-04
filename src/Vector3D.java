import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


/**
 * @author Marcello De Bernardi 25/09/2017.
 * <p>
 * Class representing a 3-dimensional vector.
 */
public class Vector3D {
    private double x, y, z;


    /**
     * Constructor for Vector3D. Arguments are dimensions of vector.
     *
     * @param x x-dimension of vector
     * @param y y-dimension of vector
     * @param z z-dimension of vector
     */
    Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * Returns a Vector3D representing the zero vector.
     *
     * @return zero vector
     */
    static Vector3D zeroVector() {
        return new Vector3D(0,0,0);
    }

    /**
     * Returns the x dimension of the vector.
     *
     * @return x coordinate
     */
    double x() {
        return x;
    }

    /**
     * Setter for the x dimension of the vector.
     *
     * @param x x dimension
     */
    void x(double x) {
        this.x = x;
    }

    /**
     * Returns the y dimension of the vector.
     *
     * @return y dimension
     */
    double y() {
        return y;
    }

    /**
     * Setter for the y dimension of the vector.
     *
     * @param y y dimension
     */
    void y(double y) {
        this.y = y;
    }

    /**
     * Returns the z dimension of the vector.
     *
     * @return z dimension
     */
    double z() {
        return z;
    }

    /**
     * Setter for the z dimension of the vector.
     * @param z z dimension
     */
    void z(double z) {
        this.z = z;
    }

    /**
     * Returns the norm of the vector
     *
     * @return norm of vector
     */
    double L2norm() {
        return sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
    }

    /**
     * Returns the dot product of the Vector3D it is called on with the Vector3D
     * passed as argument.
     *
     * @param v vector to calculate dot product with
     * @return dot product of two vectors
     */
    double dotProduct(Vector3D v) {
        return (this.x * v.x) + (this.y * v.y) + (this.z * v.z);
    }

    /**
     * Returns the cross product of the Vector3D it is called on with the Vector3D
     * passed as argument.
     *
     * @param v vector to calculate cross product with
     * @return cross product of two vectors
     */
    Vector3D crossProduct(Vector3D v) {
        return new Vector3D(
                (this.y * v.z) - (this.z * v.y),
                (this.z * v.x) - (this.x * v.z),
                (this.x * v.y) - (this.y * v.x));
    }

    /**
     * Changes the vector so as to transform it into a vector of length 1 which
     * points in the same direction as before.
     */
    void normalize() {
        double length = L2norm();

        x /= length;
        y /= length;
        z /= length;
    }

    /**
     * Returns a vector that is the additive inverse of the vector this method is called on,
     * i.e. a vector of the same length pointing in the opposite direction.
     *
     * @return opposite vector
     */
    Vector3D inverseVector() {
        return new Vector3D(-x, -y, -z);
    }

    /**
     * Returns a vector representing the transformation of this vector by a matrix passed as
     * argument.
     *
     * @param matrix transformation matrix
     * @return a new vector transformed by the given matrix
     */
    Vector3D transform(Matrix matrix) {
        // todo this is not correct, check affine transformations
        double[][] m = matrix.m;

        double x = (m[0][0] * this.x) + (m[0][1] * this.y) + (m[0][2] * this.z);
        double y = (m[1][0] * this.x) + (m[1][1] * this.y) + (m[1][2] * this.z);
        double z = (m[2][0] * this.x) + (m[2][1] * this.y) + (m[2][2] * this.z);

        return new Vector3D(x, y, z);
    }

    /**
     * Returns a string representation of vector with dimensions of the vector.
     *
     * @return string with vector dimensions
     */
    public String toString() {
        return ("Vector3D as string: (x,y,z) = (" + x + ", " + y + ", " + z + ")");
    }

    /**
     * Creates a new Vector3D object which represents a vector with the same dimensions
     * as the vector the method is called on.
     *
     * @return new Vector3D
     * @throws CloneNotSupportedException if cloning is not supported
     */
    public Vector3D clone() throws CloneNotSupportedException {
        return new Vector3D(x, y, z);
    }
}
