package graphics;


import geometry.Point3D;
import geometry.Vector3D;

/**
 * @author Marcello De Bernardi 01/10/2017.
 */
public class Camera {
    private double xMin, xMax, yMin, yMax;
    private double fcp, bcp;  // front & back clipping planes
    private double ax, bx, ay, by;


    /**
     * Constructs a parallel camera with the arguments specifying
     * the boundaries of the view plane.
     *
     * @param xMin x lower bound
     * @param xMax x upper bound
     * @param yMin y lower bound
     * @param yMax y upper bound
     */
    Camera(double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public Vector3D getViewPlaneNormal() {
        // in parallel projections VPN is a vector
        // pointing straight towards z
        return new Vector3D(0, 0, 1);
    }

    /**
     * Projects any number of Point3D points in world coordinates to Point3D
     * points intended to represent a 2D point in pixel coordinates on the
     * current viewport.
     *
     * @param points point in world coordinates
     * @return point in pixel coordinates on screen
     */
    public final Point3D[] project(Point3D... points) {
        for (int i = 0; i < points.length; i++) {
            points[i] = viewportTransform(projectionTransform(cameraTransform(points[i])));
        }

        return points;
    }

    public void setViewport(int width, int height) {
        int deltaU = width;
        int deltaV = height;
        int deltaX = (int) (xMax - xMin);
        int deltaY = (int) (yMax - yMin);

        bx = deltaU / deltaX;
        by = deltaV / deltaY;
        ax = 0 - (bx * xMin);
        ay = 0 - (by * yMin);
    }

    public double getFrontClippingPlane() {
        return fcp;
    }

    /**
     * Sets the distance of the front clipping plane from the origin in
     * world coordinates. Greater positive values set the front clipping
     * plane further from the origin.
     *
     * @param fcp front clipping plane
     */
    public void setFrontClippingPlane(double fcp) {
        this.fcp = fcp;
    }

    public double getBackClippingPlane() {
        return bcp;
    }

    /**
     * Sets the distance of the back clipping plane from the origin in world
     * coordinates. Greater negative values set the back clipping plane
     * further from the origin.
     *
     * @param bcp back clipping plane
     */
    public void setBackClippingPlane(double bcp) {
        this.bcp = bcp;
    }

    @Override
    public String toString() {
        return null;
    }


    /**
     * Transforms vertices from world coordinates to camera coordinates. In
     * orthographic projection camera coordinates and world coordinates are
     * the same
     */
    protected Point3D cameraTransform(final Point3D point) {
        return point;
    }

    /**
     * Maps vertices in camera coordinates to points on the view plan. In other
     * words, projects 3D coordinates onto a 2D plane. In orthographic projection
     * world coordinates and view plane coordinates are the same, we simply
     * disregard z coordinate
     */
    protected Point3D projectionTransform(final Point3D point) {
        return point;
    }

    /**
     * Maps points on the view plane to a pixel on the viewing device.
     */
    protected Point3D viewportTransform(final Point3D point) {
        return new Point3D((ax + bx * point.x()), (ay + by * point.y()), 0);
    }
}
