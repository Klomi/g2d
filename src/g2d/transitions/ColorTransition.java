package g2d.transitions;

import g2d.Sprite;

public class ColorTransition extends Transition {
    public static final int R = 1;
    public static final int G = 2;
    public static final int B = 4;
    public static final int A = 8;
    public static final int RGB = R | G | B;
    public static final int RGBA = R | G | B | A;

    public double fromVal[] = null;
    public double toVal[] = null;
    public int mask = RGBA;

    public ColorTransition(int mask, double timeOffset, double duration, double fromVal[], double toVal[]) {
        this.mask = mask;
        this.timeOffset = timeOffset;
        this.duration = duration;
        this.fromVal = fromVal;
        this.toVal = toVal;
    }

    public static ColorTransition rgb(double timeOffset, double duration, double fromVal[], double toVal[]) {
        return new ColorTransition(RGB, timeOffset, duration, fromVal, toVal);
    }

    public static ColorTransition rgba(double timeOffset, double duration, double fromVal[], double toVal[]) {
        return new ColorTransition(RGBA, timeOffset, duration, fromVal, toVal);
    }

    public static ColorTransition alpha(double timeOffset, double duration, double fromVal, double toVal) {
        return new ColorTransition(A, timeOffset, duration, new double[]{fromVal}, new double[]{toVal});
    }

    public void applyTo(Sprite s, double time) {
        if (!isActive(time) || (mask <= 0) || (mask > RGBA))
            return;

        int t = mask;
        double f = factor(time);
        for (int i = 0; i < fromVal.length; i++) {
            // Assume fromVal.length == toVal.length
            double val = fromVal[i] * (1 - f) + toVal[i] * f;
            if ((t & R) > 0) {
                s.color[0] = val;
                t -= R;
            }
            if ((t & G) > 0) {
                s.color[1] = val;
                t -= G;
            }
            if ((t & B) > 0) {
                s.color[2] = val;
                t -= B;
            }
            if ((t & A) > 0) {
                s.color[3] = val;
                t -= A;
            }
        }
    }
}