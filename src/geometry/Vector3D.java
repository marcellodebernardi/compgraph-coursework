package geometry;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


/**
 * @author Marcello De Bernardi 25/09/2017.
 * <p>
 * Class representing a vector in a three-dimensional space. Vector3D objects
 * have mutable state.
 *
 * The class has static methods for performing operations
 * on vectors that are best understood as resulting in new vectors.
 * These methods operate constructively and return new vectors. Instance methods,
 * on the other hand, modify the vector they are called on. They return a
 * reference to the vector to enable method chaining.
 */
public class Vector3D {
    public double x, y, z;


    /**
     * Constructor for geometry.Vector3D. Arguments are dimensions of vector.
     *
     * @param x x-dimension of vector
     * @param y y-dimension of vector
     * @param z z-dimension of vector
     */
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * Returns the vector from the first point to the second point.
     *
     * @return vector from first point to second point
     */
    public static Vector3D vector(Point3D from, Point3D to){
        return new Vector3D(to.x - from.x, to.y - from.y, to.z - from.z);
    }

    /**
     * Returns the cross product of the first vector and the second vector.
     *
     * @return cross product of two vectors
     */
    public static Vector3D crossProduct(Vector3D v1, Vector3D v2) {
        return new Vector3D(
                (v1.y * v2.z) - (v1.z * v2.y),
                (v1.z * v2.x) - (v1.x * v2.z),
                (v1.x * v2.y) - (v1.y * v2.x));
    }

    /**
     * Returns the dot product of the first vector and the second vector.
     *
     * @return dot product of two vectors
     */
    public static double dotProduct(Vector3D v1, Vector3D v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    }

    /**
     * Returns the length of the argument vector.
     *
     * @return norm of vector
     */
    public static double L2norm(Vector3D v) {
        return sqrt(pow(v.x, 2) + pow(v.y, 2) + pow(v.z, 2));
    }


    /**
     * Changes the vector so as to translate it into a vector of length 1 which
     * points in the same direction as before.
     */
    Vector3D normalize() {
        double length = L2norm(this);
        x /= length;
        y /= length;
        z /= length;

        return this;
    }

    /**
     * Transforms this vector by the given transformation matrix.
     *
     * @param matrix transformation matrix
     * @return reference to this vector
     */
    @SuppressWarnings("Duplicates")
    public Vector3D transform(Matrix matrix) {
        double[][] m = matrix.m;

        // x, y, z and w are homogeneous coordinates of the new vector
        // 4th term is of form m[n][n] because implicitly this.w = 1
        double x = (m[0][0] * this.x) + (m[0][1] * this.y) + (m[0][2] * this.z) + m[0][3];
        double y = (m[1][0] * this.x) + (m[1][1] * this.y) + (m[1][2] * this.z) + m[1][3];
        double z = (m[2][0] * this.x) + (m[2][1] * this.y) + (m[2][2] * this.z) + m[2][3];
        double w = (m[3][0] * this.x) + (m[3][1] * this.y) + (m[3][2] * this.z) + m[3][3];

        this.x = x/w;
        this.y = y/w;
        this.z = z/w;

        // allows method chaining
        return this;
    }

    @Override
    public String toString() {
        return ("Vector (x,y,z) = (" + x + ", " + y + ", " + z + ")");
    }

    @Override
    public Vector3D clone() {
        return new Vector3D(x, y, z);
    }

    @Override
    public boolean equals(Object vector) {
        return vector instanceof Vector3D
                && x == ((Vector3D)vector).x
                && y == ((Vector3D)vector).y
                && z == ((Vector3D)vector).z;
    }
}