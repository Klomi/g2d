package g2d;

@FunctionalInterface
public interface Interpolator {
    Interpolator LINEAR = (t) -> t;

    Interpolator EASEIN_QUAD = (t) -> t * t;
    Interpolator EASEOUT_QUAD = (t) -> t * (2 - t);
    Interpolator EASEINOUT_QUAD = (t) -> (t < 0.5) ? 2 * t * t : -1 + (4 - 2 * t) * t;

    Interpolator EASEIN_CUBIC = (t) -> t * t * t;
    Interpolator EASEOUT_CUBIC = (t) -> 1 + (t - 1) * (t - 1) * (t - 1);
    Interpolator EASEINOUT_CUBIC = (t) -> (t < 0.5) ? 4 * t * t * t : (t - 1) * (2 * t - 2) * (2 * t - 2) + 1;

    Interpolator EASEIN_QUART = (t) -> t * t * t * t;
    Interpolator EASEOUT_QUART = (t) -> 1 - (t - 1) * (t - 1) * (t - 1) * (t - 1);
    Interpolator EASEINOUT_QUART = (t) -> (t < 0.5) ? 8 * t * t * t * t : 1 - 8 * (t - 1) * (t - 1) * (t - 1) * (t - 1);

    Interpolator EASEIN_BACK = (t) -> {
        final double s = 1.70158;
        return t * t * ((s + 1) * t - s);
    };
    Interpolator EASEOUT_BACK = (t) -> {
        final double s = 1.70158;
        t -= 1;
        return t * t * ((s + 1) * t + s) + 1;
    };
    Interpolator EASEINOUT_BACK = (t) -> {
        final double s = 2.5949095;
        t *= 2;
        if (t < 1) {
            return 0.5 * t * t * ((s + 1) * t - s);
        } else {
            t -= 2;
            return 0.5 * (t * t * ((s + 1) * t + s)) + 1;
        }
    };

    double curve(double t);
}