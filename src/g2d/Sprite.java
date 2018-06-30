package g2d;

import g2d.render.BlendMode;
import g2d.render.Primitive;
import g2d.render.Renderer;
import g2d.transitions.Transition;
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
    public int z_index = 0;

    // Anchor
    public double ax = 0.5, ay = 0.5;

    // Scaling
    public double sx = 1, sy = 1;

    // Color RGBA
    public double color[] = {1, 1, 1, 1};

    // Rotation angles XY, XZ, YZ
    public double rot[] = {0, 0, 0};

    // Cached transform matrix
    private Matrix4d mat = null;

    // Transitions
    private List<Transition> transitions = new LinkedList<>();

    // Sprite tree
    private Sprite parent = null;
    private List<Sprite> childs = new LinkedList<>();
    public boolean relativePosition = true;
    public boolean multiplyColor = false;
    public boolean multiplyAlpha = false;

    public Sprite() {

    }

    public Sprite(Image img) {
        this.img = img;
        this.w = img.getWidth();
        this.h = img.getHeight();
    }

    public void update(double time) {
        for (Transition t : transitions)
            t.applyTo(this, time);
        mat = null;
    }

    public void render(double time) {
        update(time);

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
        final double fc[] = getColor();

        for (int i = 0; i < 6; i++) {
            prim.posData.put((float) pts[index[i]].x).put((float) pts[index[i]].y);
            prim.colorData.put((float) fc[0]).put((float) fc[1]).put((float) fc[2]).put((float) fc[3]);
            prim.texCoordData.put((float) texCoords[index[i]].x).put((float) texCoords[index[i]].y);
        }

        Renderer.push(prim);
    }

    public void addChild(Sprite s) {
        childs.add(s);
        s.parent = this;
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
            mat.rotateZYX(rot[0], rot[1], rot[2]);
            mat.scale(sx, sy, 1);
            if ((parent != null) && relativePosition)
                mat = parent.getTransformMatrix().mul(mat);
        }
        return mat;
    }

    private double[] getColor() {
        if ((parent != null) && (multiplyColor || multiplyAlpha)) {
            double c[] = {color[0], color[1], color[2], color[3]};
            double pc[] = parent.getColor();
            if(multiplyColor) {
                c[0] *= pc[0];
                c[1] *= pc[1];
                c[2] *= pc[2];
            }
            if(multiplyAlpha)
                c[3] *= pc[3];
            return c;
        }
        return color;
    }
}