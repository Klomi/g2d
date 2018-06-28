package g2d.transitions;

import g2d.Sprite;

public abstract class Transition {
    public double timeOffset = 0, duration = 0;
    public TimingFunction tFunc = TimingFunctions.LINEAR;

    public abstract void applyTo(Sprite s, double time);

    public double factor(double time) {
        double t = (time - timeOffset) / duration;
        t = Math.min(Math.max(t, 0), 1);
        return tFunc.apply(t);
    }

    public boolean isActive(double time) {
        return (time >= timeOffset) && (time <= timeOffset + duration);
    }

    public boolean isPending(double time) {
        return time < timeOffset;
    }

    public boolean isFinished(double time) {
        return time > timeOffset + duration;
    }
}