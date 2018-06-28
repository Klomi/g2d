package g2d.transitions;

import g2d.Sprite;

public class RotateTransition extends Transition {
    public static final int X = 1;
    public static final int Y = 2;
    public static final int Z = 4;

    public double fromVal[] = null;
    public double toVal[] = null;
    public int mask = Z;

    public RotateTransition(int mask, double timeOffset, double duration, double fromVal[], double toVal[]) {
        this.mask = mask;
        this.timeOffset = timeOffset;
        this.duration = duration;
        this.fromVal = fromVal;
        this.toVal = toVal;
    }

    public static RotateTransition rotateZ(double timeOffset, double duration, double fromVal, double toVal) {
        return new RotateTransition(Z, timeOffset, duration, new double[]{fromVal}, new double[]{toVal});
    }

    public static RotateTransition rotateZYX(double timeOffset, double duration, double fromVal[], double toVal[]) {
        return new RotateTransition(X | Y | Z, timeOffset, duration, fromVal, toVal);
    }

    public void applyTo(Sprite s, double time) {
        if (!isActive(time) || (mask <= 0) || (mask > (X | Y | Z)))
            return;

        int t = mask;
        double f = factor(time);
        for (int i = 0; i < fromVal.length; i++) {
            // Assume fromVal.length == toVal.length
            double val = fromVal[i] * (1 - f) + toVal[i] * f;
            if ((t & Z) > 0) {
                s.rot[0] = val;
                t -= Z;
            }
            if ((t & Y) > 0) {
                s.rot[1] = val;
                t -= Y;
            }
            if ((t & X) > 0) {
                s.rot[2] = val;
                t -= X;
            }
        }
    }
}