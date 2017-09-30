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
     * Returns a string representation of the point giving its coordinates
     *
     * @return point coordinates as string
     */
    public String toString() {
        return ("Point3D as string: (x,y,z) = (" + x + ", " + y + ", " + z + ")");
    }
}