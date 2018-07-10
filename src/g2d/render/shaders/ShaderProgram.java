package g2d.render.shaders;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    public int id = 0;
    public Map<String, Integer> varLocations = new HashMap<>();

    public ShaderProgram() {
        id = glCreateProgram();
    }

    public void attachShader(Shader... shaders) {
        for (Shader s : shaders)
            glAttachShader(id, s.id);
    }

    public boolean linkProgram() {
        glLinkProgram(id);
        return glGetProgrami(id, GL_LINK_STATUS) == GL_TRUE;
    }

    public String getLinkerMessage() {
        return glGetProgramInfoLog(id);
    }

    public int getAttributeLocation(String name) {
        return glGetAttribLocation(id, name);
    }

    public int getUniformLocation(String name) {
        return glGetUniformLocation(id, name);
    }

    public void bind() {
        glUseProgram(id);
    }
}