package graphics;

import geometry.Matrix;
import geometry.Point3D;
import geometry.Vector3D;

/**
 * @author Marcello De Bernardi 01/10/2017.
 */
public class PerspectiveCamera extends Camera {
    public Point3D cop;                // centre of projection, also origin of camera coordinates
    public Vector3D vuv;               // view up vector (axis v)
    public Matrix m;                   // camera transformation matrix
    public Point3D vrp;                // view reference point: lies in view plane
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

        cop = new Point3D(0, 0, 3);
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
        return new Point3D(point.x, point.y, point.z)
                .transform(m.setIdentity().setTranslation(-cop.x, -cop.y, -cop.z));
    }

    @Override
    protected Point3D projectionTransform(final Point3D point) {
        return new Point3D(point.x, point.y, point.z)
                .transform(m.setProjection(cop.z - vrp.z));
    }

    /**
     * Allows arbitrary translations on the center of projection, enabling
     * camera motion.
     *
     */
    public void translate(Vector3D vector) {
        cop.add(vector);
        vrp.add(vector);

        bcp += vector.z;
        fcp += vector.z;
    }

    @Override
    public String toString() {
        return "Camera:\n"
                + "fcp: " + fcp + "\n"
                + "bcp: " + bcp + "\n"
                + "cop: " + cop + "\n"
                + "vrp: " + vrp + "\n"
                + "vpn: " + vpn + "\n"
                + "vuv: " + vuv + "\n";
    }
}