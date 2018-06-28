package g2d.transitions;

import g2d.Sprite;

public class ScaleTransition extends Transition {
    public static final int X = 1;
    public static final int Y = 2;
    public static final int XY = X | Y;

    public double fromVal[] = null;
    public double toVal[] = null;
    public int mask = XY;

    public ScaleTransition(int mask, double timeOffset, double duration, double fromVal[], double toVal[]) {
        this.mask = mask;
        this.timeOffset = timeOffset;
        this.duration = duration;
        this.fromVal = fromVal;
        this.toVal = toVal;
    }

    public static ScaleTransition scaleX(double timeOffset, double duration, double fromVal, double toVal) {
        return new ScaleTransition(X, timeOffset, duration, new double[]{fromVal}, new double[]{toVal});
    }

    public static ScaleTransition scaleY(double timeOffset, double duration, double fromVal, double toVal) {
        return new ScaleTransition(Y, timeOffset, duration, new double[]{fromVal}, new double[]{toVal});
    }

    public static ScaleTransition scaleXY(double timeOffset, double duration, double fromVal[], double toVal[]) {
        return new ScaleTransition(XY, timeOffset, duration, fromVal, toVal);
    }

    public void applyTo(Sprite s, double time) {
        if (!isActive(time) || (mask <= 0) || (mask > XY))
            return;

        int t = mask;
        double f = factor(time);
        for (int i = 0; i < fromVal.length; i++) {
            // Assume fromVal.length == toVal.length
            double val = fromVal[i] * (1 - f) + toVal[i] * f;
            if ((t & X) > 0) {
                s.sx = val;
                t -= X;
            }
            if ((t & Y) > 0) {
                s.sy = val;
                t -= Y;
            }
        }
    }
}