package g2d.render;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    public int id = 0;

    public Shader(int type) {
        id = glCreateShader(type);
    }

    public void setSourceCode(String sourceCode) {
        glShaderSource(id, sourceCode);
    }

    public boolean compile() {
        glCompileShader(id);
        return glGetShaderi(id, GL_COMPILE_STATUS) == GL_TRUE;
    }

    public String getCompilerMessage() {
        return glGetShaderInfoLog(id);
    }
}