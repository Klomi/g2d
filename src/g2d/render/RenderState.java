package g2d.render;

import static org.lwjgl.opengl.GL11.*;

public final class RenderState {
    public int primType = 0;
    public int tID = 0;
    public BlendMode blendMode = BlendMode.SRC_OVER;

    public boolean canBatchWith(RenderState rs) {
        switch (this.primType) {
            case GL_LINE_STRIP:
            case GL_LINE_LOOP:
            case GL_TRIANGLE_STRIP:
            case GL_TRIANGLE_FAN:
                return false;
            default:
                // GL_POINTS, GL_LINES, GL_TRIANGLES
                return (this.primType == rs.primType) && (this.tID == rs.tID) && this.blendMode.equals(rs.blendMode);
        }
    }
}