package g2d.transitions;

public class TimingFunctions {
    public static final TimingFunction LINEAR = (t) -> t;
    public static final TimingFunction SMOOTH_STEP = (t) -> t * t * (3 - 2 * t);
}