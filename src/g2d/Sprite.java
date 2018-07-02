package g2d;

import g2d.render.BlendMode;
import g2d.render.Primitive;
import g2d.render.Renderer;
import org.joml.Matrix4d;
import org.joml.Vector2d;
import org.joml.Vector4d;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Sprite {
    public Image img = null;
    public BlendMode blendMode = BlendMode.SRC_OVER;

    // Bounds
    public double x = 0, y = 0, w = 0, h = 0;

    // Render priority
    public int zIndex = 0;

    // Anchors
    public double ax = 0.5, ay = 0.5;

    // Scaling
    public double sx = 1, sy = 1;

    // Color RGBA
    public double color[] = new double[]{1, 1, 1, 1};

    // Rotation angles alpha, beta, gamma
    public double rotation[] = new double[]{0, 0, 0};

    // Cached transform matrix
    private Matrix4d mat = null;

    // Transitions
    private List<Transition> transitions = new LinkedList<>();

    // Sprite tree
    private Sprite parent = null;
    private List<Sprite> childs = new LinkedList<>();
    public boolean positionRelativeToParent = true;
    public boolean multiplyParentColor = false;
    public boolean multiplyParentAlpha = false;

    public Sprite() {

    }

    public Sprite(Image img) {
        this.img = img;
        this.w = img.getWidth();
        this.h = img.getHeight();
    }

    public double get(InterpolatableProperties prop) {
        switch (prop) {
            case X:
                return x;
            case Y:
                return y;
            case SCALE_X:
                return sx;
            case SCALE_Y:
                return sy;
            case RED:
                return color[0];
            case GREEN:
                return color[1];
            case BLUE:
                return color[2];
            case ALPHA:
                return color[3];
            case ROTATION_ALPHA:
                return rotation[0];
            case ROTATION_BETA:
                return rotation[1];
            case ROTATION_GAMMA:
                return rotation[2];
        }
        return 0;
    }

    public void set(InterpolatableProperties prop, double val) {
        switch (prop) {
            case X:
                x = val;
                break;
            case Y:
                y = val;
                break;
            case SCALE_X:
                sx = val;
                break;
            case SCALE_Y:
                sy = val;
                break;
            case RED:
                color[0] = val;
                break;
            case GREEN:
                color[1] = val;
                break;
            case BLUE:
                color[2] = val;
                break;
            case ALPHA:
                color[3] = val;
                break;
            case ROTATION_ALPHA:
                rotation[0] = val;
                break;
            case ROTATION_BETA:
                rotation[1] = val;
                break;
            case ROTATION_GAMMA:
                rotation[2] = val;
                break;
        }
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(double w, double h) {
        this.w = w;
        this.h = h;
    }

    public void setScale(double sx, double sy) {
        this.sx = sx;
        this.sy = sy;
    }

    public void setAnchor(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }

    public void setColor(double r, double g, double b) {
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;
    }

    public void setColor(double r, double g, double b, double a) {
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;
        this.color[3] = a;
    }

    public void setRed(double r) {
        this.color[0] = r;
    }

    public void setGreen(double g) {
        this.color[1] = g;
    }

    public void setBlue(double b) {
        this.color[2] = b;
    }

    public void setAlpha(double a) {
        this.color[3] = a;
    }

    public void setRotation(double... components) {
        System.arraycopy(components, 0, this.color, 0, Math.min(components.length, 3));
    }

    public void translate(double tx, double ty) {
        this.x += tx;
        this.y += ty;
    }

    public void scaleBy(double sx, double sy) {
        this.sx *= sx;
        this.sy *= sy;
    }

    public void rotateBy(double... components) {
        for (int i = 0; i < Math.min(components.length, 3); i++)
            this.rotation[i] += components[i];
    }

    public void update(double time) {
        for (Transition t : transitions)
            t.update(this, time);
        mat = null;
    }

    public void render(double time) {
        update(time);
        for (Sprite s : childs)
            s.render(time);
        if ((w == 0) || (h == 0))
            return;

        Primitive prim = new Primitive(GL_TRIANGLES, 6);
        prim.rs.blendMode = blendMode;
        prim.rs.tID = (img != null) ? img.getTexture().id : 1;

        final int index[] = {0, 1, 2, 0, 2, 3};
        Vector4d pts[] = new Vector4d[]{
                new Vector4d(-w * ax, -h * ay, 0, 1),
                new Vector4d(-w * ax, h * (1 - ay), 0, 1),
                new Vector4d(w * (1 - ax), h * (1 - ay), 0, 1),
                new Vector4d(w * (1 - ax), -h * ay, 0, 1),
        };
        for (int i = 0; i < 4; i++)
            pts[i] = getTransformMatrix().transform(pts[i]);
        final Vector2d texCoords[] = img.getTexCoords();
        final double fc[] = calcColor();

        for (int i = 0; i < 6; i++) {
            prim.posData.put((float) pts[index[i]].x).put((float) pts[index[i]].y);
            prim.colorData.put((float) fc[0]).put((float) fc[1]).put((float) fc[2]).put((float) fc[3]);
            prim.texCoordData.put((float) texCoords[index[i]].x).put((float) texCoords[index[i]].y);
        }

        Renderer.push(prim);
    }

    public void addChild(Sprite s) {
        if (s.parent != null)
            s.parent.removeChild(s);
        s.parent = this;
        childs.add(s);
    }

    public void removeChild(Sprite s) {
        if (s.parent == this) {
            childs.remove(s);
            s.parent = null;
        }
    }

    public void clearChilds() {
        while (childs.size() > 0)
            removeChild(childs.get(0));
    }

    public Sprite getParent() {
        return parent;
    }

    public void addTransition(Transition t) {
        transitions.add(t);
    }

    public void removeTransition(Transition t) {
        transitions.remove(t);
    }

    public void clearTransitions() {
        transitions.clear();
    }

    private Matrix4d getTransformMatrix() {
        if (mat == null) {
            mat = new Matrix4d();
            mat.translate(x, y, 0);
            mat.rotateZYX(rotation[0], rotation[1], rotation[2]);
            mat.scale(sx, sy, 1);
            if ((parent != null) && positionRelativeToParent)
                mat = parent.getTransformMatrix().mul(mat);
        }
        return mat;
    }

    private double[] calcColor() {
        double c[] = {color[0], color[1], color[2], color[3]};
        if ((parent != null) && (multiplyParentColor || multiplyParentAlpha)) {
            double pc[] = parent.calcColor();
            if (multiplyParentColor) {
                c[0] *= pc[0];
                c[1] *= pc[1];
                c[2] *= pc[2];
            }
            if (multiplyParentAlpha)
                c[3] *= pc[3];
        }
        return c;
    }

    public enum InterpolatableProperties {
        X, Y, SCALE_X, SCALE_Y,
        ROTATION_ALPHA, ROTATION_BETA, ROTATION_GAMMA,
        RED, GREEN, BLUE, ALPHA
    }
}