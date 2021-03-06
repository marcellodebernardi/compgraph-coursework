package geometry;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author Marcello De Bernardi 25/09/2017.
 * <p>
 * Class representing a point in a three-dimensional space. Point3D objects
 * have mutable state.
 *
 * The class has static methods for performing operations
 * on points that aren't best understood as modifications to a particular
 * point. Instance methods, on the other hand, modify the point they
 * are called on. They return a reference to the point to enable
 * method chaining.
 */
public class Point3D {
    public double x, y, z;


    /**
     * Constructor. Arguments are the coordinates of the point
     * in 3D space.
     *
     * @param X x-coordinate of point
     * @param Y y-coordinate of point
     * @param Z z-coordinate of point
     */
    public Point3D(double X, double Y, double Z) {
        x = X;
        y = Y;
        z = Z;
    }


    /**
     * Returns the distance in 3d space between the point on which
     * the method is called, and the point passed as argument.
     *
     * @return distance scalar
     */
    static double distance(Point3D p1, Point3D p2) {
        return sqrt(pow(p1.x - p2.x, 2) + pow(p1.y - p2.y, 2) + pow(p1.z - p2.z, 2));
    }

    /**
     * Translates this point by a given vector. Mutates the state of the
     * point.
     *
     * @param vector translation vector
     * @return reference to this same point
     */
    public Point3D add(Vector3D vector) {
        x += vector.x;
        y += vector.y;
        z += vector.z;

        return this;
    }

    /**
     * Transforms the point by the given transformation matrix. This
     * method changes the state of the point.
     *
     * @param matrix transformation matrix
     * @return reference to same point
     */
    @SuppressWarnings("Duplicates")
    public Point3D transform(Matrix matrix) {
        double[][] m = matrix.m;

        // x, y, z and w are homogeneous coordinates of the new point
        // 4th term is of form m[n][n] because implicitly this.w = 1
        double x = (m[0][0] * this.x) + (m[0][1] * this.y) + (m[0][2] * this.z) + m[0][3];
        double y = (m[1][0] * this.x) + (m[1][1] * this.y) + (m[1][2] * this.z) + m[1][3];
        double z = (m[2][0] * this.x) + (m[2][1] * this.y) + (m[2][2] * this.z) + m[2][3];
        double w = (m[3][0] * this.x) + (m[3][1] * this.y) + (m[3][2] * this.z) + m[3][3];

        // convert to 3-space coordinate
        this.x = x/w;
        this.y = y/w;
        this.z = z/w;

        return this;
    }

    @Override
    public String toString() {
        return ("Point (x,y,z) = (" + x + ", " + y + ", " + z + ")");
    }

    @Override
    public Point3D clone() {
        return new Point3D(x, y, z);
    }

    @Override
    public boolean equals(Object point) {
        return point instanceof Point3D
                && x == ((Point3D)point).x
                && y == ((Point3D)point).y
                && z == ((Point3D)point).z;
    }
}