import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author Marcello De Bernardi 25/09/2017.
 * <p>
 * Class representing a point in a three-dimensional space.
 */
class Point3D {
    private double x, y, z;


    /**
     * Constructor. Arguments are the coordinates of the point
     * in 3D space.
     *
     * @param X x-coordinate of point
     * @param Y y-coordinate of point
     * @param Z z-coordinate of point
     */
    Point3D(double X, double Y, double Z) {
        x = X;
        y = Y;
        z = Z;
    }


    /**
     * Returns a Point3D placed at the origin of the coordinate space.
     *
     * @return point at origin of coordinate space
     */
    static Point3D origin() {
        return new Point3D(0, 0, 0);
    }

    /**
     * Returns the surface normal vector of a face defined by the three points
     * passed as arguments.
     *
     * @param p1 first vertex of face
     * @param p2 second vertex of face
     * @param p3 third vertex of face
     * @return surface normal vector
     */
    static Vector3D faceNormal(Point3D p1, Point3D p2, Point3D p3){
        // computes the surface normal of a face defined by the three argument points
        return p1.vector(p2).crossProduct(p1.vector(p3));
    }

    /**
     * Returns true if the face defined by the three points passed as arguments is
     * a front face from the viewpoint defined by the viewpoint vector.
     *
     * @param p1 first vertex of face
     * @param p2 second vertex of face
     * @param p3 third vertex of face
     * @param vpn viewpoint vector
     * @return true if front face
     */
    static boolean isFrontFace(Point3D p1, Point3D p2, Point3D p3, Vector3D vpn){
        return faceNormal(p1, p2, p3).dotProduct(vpn) > 0;
    }

    /**
     * Returns the x coordinate of the point.
     *
     * @return x coordinate
     */
    double x() {
        return x;
    }

    /**
     * Setter for x coordinate of the point.
     *
     * @param x x coordinate
     */
    void x(double x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate of the point.
     *
     * @return y coordinate
     */
    double y() {
        return y;
    }

    /**
     * Setter for the y coordinate of the point.
     *
     * @param y y coordinate
     */
    void y(double y) {
        this.y = y;
    }

    /**
     * Return the z coordinate of the point.
     *
     * @return z coordinate
     */
    double z() {
        return z;
    }

    /**
     * Setter for the z coordinate of the point.
     * @param z
     */
    void z(double z) {
        this.z = z;
    }

    /**
     * Returns the distance in 3d space between the point on which
     * the method is called, and the point passed as argument.
     *
     * @param p point to calculate distance from
     * @return distance scalar
     */
    double distance(Point3D p) {
        return sqrt(pow(x - p.x, 2) + pow(y - p.y, 2) + pow(z - p.z, 2));
    }

    /**
     * Returns a point representing the transformation of the point this method is called on
     * by a matrix passed as argument.
     *
     * @param matrix transform matrix
     * @return new point
     */
    Point3D transform(Matrix matrix) {
        double[][] m = matrix.m;

        double x = (m[0][0] * this.x) + (m[0][1] * this.y) + (m[0][2] * this.z) + m[0][3];
        double y = (m[1][0] * this.x) + (m[1][1] * this.y) + (m[1][2] * this.z) + m[1][3];
        double z = (m[2][0] * this.x) + (m[2][1] * this.y) + (m[2][2] * this.z) + m[2][3];

        double w = (m[3][0] * this.x) + (m[3][1] * this.y) + (m[3][2] * this.z) + m[3][3];

        return new Point3D(x/w, y/w, z/w);
    }

    /**
     * Returns the vector between this point and the point passed as argument.
     *
     * @param point point to which new vector extends from this point
     * @return new vector between this point and argument point
     */
    Vector3D vector(Point3D point){
        return new Vector3D(point.x - x, point.y - y, point.z - z);
    }

    /**
     * Returns a string representation of the point giving its coordinates
     *
     * @return point coordinates as string
     */
    public String toString() {
        return ("Point3D as string: (x,y,z) = (" + x + ", " + y + ", " + z + ")");
    }
}