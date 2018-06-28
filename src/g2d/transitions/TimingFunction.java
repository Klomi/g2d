package g2d.transitions;

@FunctionalInterface
public interface TimingFunction {
    // [0, 1] -> [0, 1]
    double apply(double t);
}