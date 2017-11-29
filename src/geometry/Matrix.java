package geometry;

/**
 * @author Marcello De Bernardi 01/10/2017.
 */
public class Matrix {
    double[][] m;             // Should be 4x4


    public Matrix() {
        m = new double[4][4];
        setIdentity();
    }


    public Matrix setIdentity() {   /* Resets the matrix to the Identity matrix */
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (i == j) m[i][i] = 1.0;
                else m[i][j] = m[j][i] = 0.0;
        return this;
    }

    /**
     * Returns a new geometry.Matrix representing the result of multiplying the matrix
     * the method is called on by the matrix supplied as argument.
     *
     * @param a matrix to multiply this matrix on
     * @return new matrix for multiplication result
     */
    public Matrix multiply(Matrix a) {
        Matrix result = new Matrix();

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                result.m[i][j] = 0.0;
                for (int k = 0; k < 4; k++)
                    result.m[i][j] += (m[i][k] * a.m[k][j]);
            }

        return result;
    }

    public Matrix setTranslation(double tx, double ty, double tz) {
        this.setIdentity();
        m[0][3] = tx;
        m[1][3] = ty;
        m[2][3] = tz;

        return this;
    }

    public Matrix setRotationX(double a) {
        double cs, sn;

        this.setIdentity();
        cs = Math.cos(a);
        sn = Math.sin(a);
        m[1][1] = cs;
        m[2][1] = sn;
        m[1][2] = -sn;
        m[2][2] = cs;

        return this;
    }

    public Matrix setRotationY(double a) {
        double cs, sn;

        this.setIdentity();
        cs = Math.cos(a);
        sn = Math.sin(a);
        m[0][0] = cs;
        m[2][0] = -sn;
        m[0][2] = sn;
        m[2][2] = cs;

        return this;
    }

    public Matrix setRotationZ(double a) {
        double cs, sn;

        this.setIdentity();
        cs = Math.cos(a);
        sn = Math.sin(a);
        m[0][0] = cs;
        m[0][1] = -sn;
        m[1][0] = sn;
        m[1][1] = cs;

        return this;
    }

    /**
     * Returns a transformation matrix for projecting a point in camera
     * coordinates to view plane coordinates.
     *
     * @param distance distance between camera and view plane
     * @return projection matrix
     */
    public Matrix setProjection(double distance) {
        this.setIdentity();

        m[3][3] = 0;
        m[3][2] = 1/distance;

        return this;
    }

    /**
     * Returns the inverse matrix of the matrix the method is called on.
     *
     * @return inverse matrix
     */
    public Matrix inverse() {
        double det;
        int i, j;
        Matrix invM = new Matrix();

        det = m[0][0] * (m[1][1] * m[2][2] - m[2][1] * m[1][2]) +
                m[0][1] * (m[1][2] * m[2][0] - m[2][2] * m[1][0]) +
                m[0][2] * (m[1][0] * m[2][1] - m[2][0] * m[1][1]);

        if (det == 0.0) {
            System.out.println("singular transformation matrix\n");
            System.exit(0);
        }

        invM.m[0][0] = m[1][1] * m[2][2] - m[1][2] * m[2][1];
        invM.m[1][0] = m[1][2] * m[2][0] - m[1][0] * m[2][2];
        invM.m[2][0] = m[1][0] * m[2][1] - m[1][1] * m[2][0];

        invM.m[0][1] = m[0][2] * m[2][1] - m[0][1] * m[2][2];
        invM.m[1][1] = m[0][0] * m[2][2] - m[0][2] * m[2][0];
        invM.m[2][1] = m[0][1] * m[2][0] - m[0][0] * m[2][1];

        invM.m[0][2] = m[0][1] * m[1][2] - m[0][2] * m[1][1];
        invM.m[1][2] = m[0][2] * m[1][0] - m[0][0] * m[1][2];
        invM.m[2][2] = m[0][0] * m[1][1] - m[0][1] * m[1][0];

        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                invM.m[i][j] /= det;

        for (j = 0; j < 3; j++) {
            invM.m[j][3] = 0.0;
            for (i = 0; i < 3; i++) invM.m[j][3] -= (m[i][3] * invM.m[j][i]);
        }
        invM.m[3][3] = 1.0;
        return invM;
    }
}

