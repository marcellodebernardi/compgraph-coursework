package geometry;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


/**
 * @author Marcello De Bernardi 25/09/2017.
 * <p>
 * Class representing a 3-dimensional vector. Vector3D is an immutable
 * class and does not support spurious changes.
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


    public static Vector3D copy(Vector3D v) {
        return new Vector3D(v.x, v.y, v.z);
    }

    /**
     * Returns the vector between this point and the point passed as argument.
     *
     * @return new vector between this point and argument point
     */
    public static Vector3D vector(Point3D from, Point3D to){
        return new Vector3D(to.x - from.x, to.y - from.y, to.z - from.z);
    }

    /**
     * Returns the cross product of the geometry.Vector3D it is called on with the
     * geometry.Vector3D passed as argument.
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
     * Returns the dot product of the geometry.Vector3D it is called on with the
     * geometry.Vector3D passed as argument.
     *
     * @return dot product of two vectors
     */
    public static double dotProduct(Vector3D v1, Vector3D v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    }

    /**
     * Returns the norm of the vector
     *
     * @return norm of vector
     */
    public static double L2norm(Vector3D v) {
        return sqrt(pow(v.x, 2) + pow(v.y, 2) + pow(v.z, 2));
    }


    /**
     * Changes the vector so as to transform it into a vector of length 1 which
     * points in the same direction as before.
     */
    Vector3D normalize() {
        double length = L2norm(this);
        return new Vector3D(x / length, y / length, z / length);
    }

    /**
     * Returns a vector that is the additive inverse of the vector this method is called on,
     * i.e. a vector of the same length pointing in the opposite direction.
     *
     * @return opposite vector
     */
    Vector3D inverse() {
        return new Vector3D(-x, -y, -z);
    }

    /**
     * Returns a vector representing the transformation of this vector by a matrix passed as
     * argument.
     *
     * @param matrix transformation matrix
     * @return a new vector transformed by the given matrix
     */
    @SuppressWarnings("Duplicates")
    public void transform(Matrix matrix) {
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
    }

    @Override
    public String toString() {
        return ("Vector (x,y,z) = (" + x + ", " + y + ", " + z + ")");
    }

    @Override
    public Vector3D clone() throws CloneNotSupportedException {
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