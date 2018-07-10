package g2d.render.shaders;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class DefaultVertexShader extends Shader {
    public DefaultVertexShader() {
        super(GL_VERTEX_SHADER);

        String code = "";
        code += "#version 430 core\n";
        code += "uniform mat4 matrix;\n";
        code += "in vec2 in_pos;\n";
        code += "in vec4 in_color;\n";
        code += "in vec2 in_texCoord;\n";
        code += "out vec4 color;\n";
        code += "out vec2 texCoord;\n";
        code += "void main(){\n";
        code += "    gl_Position = matrix * vec4(in_pos, 0, 1);\n";
        code += "    color = in_color;\n";
        code += "    texCoord = in_texCoord;\n";
        code += "}\n";
        setSourceCode(code);

        if (!compile())
            System.err.println(getCompilerMessage());
    }
}