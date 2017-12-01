package graphics;

import geometry.Matrix;
import geometry.Point3D;
import geometry.Vector3D;

/**
 * @author Marcello De Bernardi 01/10/2017.
 */
public class PerspectiveCamera extends Camera {
    public Point3D cop;                // centre of projection
    public Vector3D vuv;               // view up vector (axis v)
    public Matrix m;                   // camera transformation matrix
    public Point3D vrp;                // view reference point: the origin of camera coordinating system
    public Vector3D vpn;               // view plane normal (axis u)


    /**
     * Constructs a PerspectiveCamera with the arguments indicating
     * the boundaries of the view plane.
     *
     * @param xMin x lower bound
     * @param xMax x upper bound
     * @param yMin y lower bound
     * @param yMax y upper bound
     */
    PerspectiveCamera(double xMin, double xMax, double yMin, double yMax) {
        super(xMin, xMax, yMin, yMax);

        cop = new Point3D(0, 0, -4);
        m = new Matrix();
        vrp = new Point3D(0, 0, 0);
        vpn = new Vector3D(0, 0, 1);
        vuv = new Vector3D(0, 1, 0);
    }

    public Vector3D getViewPlaneNormal() {
        return vpn;
    }

    @Override
    protected Point3D cameraTransform(final Point3D point) {
        Point3D newPoint = new Point3D(point.x, point.y, point.z);
        newPoint.transform(m.setIdentity().setTranslation(-cop.x, -cop.y, -cop.z));
        return newPoint;
    }

    @Override
    protected Point3D projectionTransform(final Point3D point) {
        Point3D newPoint = new Point3D(point.x, point.y, point.z);
        newPoint.transform(new Matrix().setProjection(-cop.z));
        return newPoint;
    }

    /**
     * Sets the center of projection for the camera.
     *
     * @param cop center of projection
     */
    public void setCOP(Point3D cop) {
        this.cop = cop;
    }

    /**
     * Allows arbitrary translations on the center of projection, enabling
     * camera motion.
     *
     * @param transformation transformation matrix applied to the COP
     */
    public void transform(Matrix transformation) {
        cop.transform(transformation);
        // todo vuv = vuv.transform(transformation);
    }

    public void setupUVN(Point3D vrp, Vector3D vpn, Vector3D vuv) {
        this.vrp = vrp;
        this.vpn = vpn;
        this.vuv = vuv;
    }

    @Override
    public String toString() {
        return "Camera:\n"
                + "fcp: " + getFrontClippingPlane() + "\n"
                + "bcp: " + getBackClippingPlane() + "\n"
                + "cop: " + cop + "\n"
                + "vrp: " + vrp + "\n"
                + "vpn: " + vpn + "\n"
                + "vuv: " + vuv + "\n";
    }
}