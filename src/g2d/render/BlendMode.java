package g2d.render;

import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL20.glBlendEquationSeparate;

public final class BlendMode {
    public int funcRGB = 0, funcAlpha = 0;
    public int srcFactor = 0, dstFactor = 0;
    public int srcAlpha = 0, dstAlpha = 0;

    public static final BlendMode SRC_OVER = new BlendMode(GL_FUNC_ADD, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    public static final BlendMode ADD = new BlendMode(GL_FUNC_ADD, GL_SRC_ALPHA, GL_ONE);
    public static final BlendMode INVERT = new BlendMode(GL_FUNC_SUBTRACT, GL_SRC_ALPHA, GL_ONE);
    public static final BlendMode COPY = new BlendMode(GL_FUNC_ADD, GL_ONE, GL_ZERO);

    public BlendMode(int funcRGB, int srcFactor, int dstFactor) {
        this.funcRGB = funcRGB;
        this.srcFactor = srcFactor;
        this.dstFactor = dstFactor;
    }

    public BlendMode(int funcRGB, int srcFactor, int dstFactor, int srcAlpha, int dstAlpha) {
        this(funcRGB, srcFactor, dstFactor);
        this.srcAlpha = srcAlpha;
        this.dstAlpha = dstAlpha;
    }

    public BlendMode(int funcRGB, int funcAlpha, int srcFactor, int dstFactor, int srcAlpha, int dstAlpha) {
        this(funcRGB, srcFactor, dstFactor);
        this.funcAlpha = funcAlpha;
        this.srcAlpha = srcAlpha;
        this.dstAlpha = dstAlpha;
    }

    public void apply() {
        if (funcAlpha == 0)
            glBlendEquation(funcRGB);
        else
            glBlendEquationSeparate(funcRGB, funcAlpha);
        if ((srcAlpha == 0) && (dstAlpha == 0))
            glBlendFunc(srcFactor, dstFactor);
        else
            glBlendFuncSeparate(srcFactor, dstFactor, srcAlpha, dstAlpha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlendMode blendMode = (BlendMode) o;
        return funcRGB == blendMode.funcRGB &&
                funcAlpha == blendMode.funcAlpha &&
                srcFactor == blendMode.srcFactor &&
                dstFactor == blendMode.dstFactor &&
                srcAlpha == blendMode.srcAlpha &&
                dstAlpha == blendMode.dstAlpha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(funcRGB, funcAlpha, srcFactor, dstFactor, srcAlpha, dstAlpha);
    }
}