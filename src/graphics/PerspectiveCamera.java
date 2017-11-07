package graphics;

import geometry.Matrix;
import geometry.Point3D;
import geometry.Vector3D;

/**
 * @author Marcello De Bernardi 01/10/2017.
 */
public class PerspectiveCamera extends Camera {
    private Point3D cop = new Point3D(0, 0, -4); // centre of projection
    private Matrix m = new Matrix(); // camera transformation matrix
    private Point3D vrp = new Point3D(0, 0, 0); // view reference point: the origin of camera coordinating system
    private Vector3D vpn = new Vector3D(0, 0, 1); // view plane normal (axis u)
    private Vector3D vuv = new Vector3D(0, 1, 0);  // view up vector (axis v)

    {
        m.setIdentity();
    }

    public PerspectiveCamera(double xMin, double xMax, double yMin, double yMax) {
        super(xMin, xMax, yMin, yMax);
    }

    public Vector3D getViewPlaneNormal() {
        return super.getViewPlaneNormal();
    }

    private Point3D projectionTransform(final Point3D p) {
        return p;
    }

    public void setupCOP(Point3D cop_) {
    }

    private Point3D cameraTransform(final Point3D p) {
        return p;
    }

    public void setupUVN(Point3D vrp_, Vector3D vpn_, Vector3D vuv_) {
    }
}