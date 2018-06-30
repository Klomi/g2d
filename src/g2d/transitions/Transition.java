package g2d.transitions;

import g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class Transition {
    public double timeOffset = 0, duration = 0;
    public Map<String, double[]> propValues = new HashMap<>();
    public Interpolator interpolator = Interpolator.LINEAR;

    public Transition(double timeOffset, double duration, String properties, double[]... values) {
        this(timeOffset, duration, Interpolator.LINEAR, properties, values);
    }

    public Transition(double timeOffset, double duration, Interpolator interpolator, String properties, double[]... values) {
        this.timeOffset = timeOffset;
        this.duration = duration;
        this.interpolator = interpolator;

        String segments[] = properties.split("\\|");
        if (segments.length != values.length)
            throw new IllegalArgumentException("number of properties != values.length");
        for (int i = 0; i < segments.length; i++)
            propValues.put(segments[i], values[i]);
    }

    public void applyTo(Sprite s, double time) {
        double t = (time - timeOffset) / duration;
        t = Math.min(Math.max(t, 0), 1);
        t = interpolator.curve(t);
        for (Map.Entry<String, double[]> entry : propValues.entrySet()) {
            double vals[] = entry.getValue();
            s.set(entry.getKey(), vals[0] * (1 - t) + vals[1] * t);
        }
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