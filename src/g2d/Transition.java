package g2d;

import java.util.LinkedList;
import java.util.List;

public class Transition {
    private double timeOffset = 0, duration = 0;
    private Interpolator interpolator = Interpolator.LINEAR;
    private List<PropertyChange> propertyChanges = new LinkedList<>();

    public Transition() {
    }

    public void applyTo(Sprite s) {
        s.addTransition(this);
    }

    public double getTimeOffset() {
        return timeOffset;
    }

    public double getDuration() {
        return duration;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void transit(Sprite s, double time) {
        double t = (time - timeOffset) / duration;
        t = Math.min(Math.max(t, 0), 1);
        t = interpolator.curve(t);
        for (PropertyChange c : propertyChanges)
            s.set(c.propName, c.fromVal * (1 - t) + c.toVal * t);
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

    public static class Builder {
        protected Transition t = new Transition();

        public Builder timeOffset(double timeOffset) {
            t.timeOffset = timeOffset;
            return this;
        }

        public Builder duration(double duration) {
            t.duration = duration;
            return this;
        }

        public Builder interpolator(Interpolator interpolator) {
            t.interpolator = interpolator;
            return this;
        }

        public Builder change(String propName, double fromVal, double toVal) {
            PropertyChange c = new PropertyChange();
            c.propName = propName;
            c.fromVal = fromVal;
            c.toVal = toVal;
            t.propertyChanges.add(c);
            return this;
        }

        public Transition build() {
            return t;
        }
    }

    private static class PropertyChange {
        String propName = null;
        double fromVal = 0, toVal = 0;
    }
}