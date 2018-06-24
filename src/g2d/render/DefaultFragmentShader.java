package g2d.render;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;

public class DefaultFragmentShader extends Shader {
    public DefaultFragmentShader() {
        super(GL_FRAGMENT_SHADER);

        String code = "";
        code += "#version 430 core\n";
        code += "uniform sampler2D tex;\n";
        code += "in vec4 color;\n";
        code += "in vec2 texCoord;\n";
        code += "out vec4 fColor;\n";
        code += "void main(){\n";
        code += "    fColor = texture(tex, texCoord) * color;\n";
        code += "}\n";
        setSourceCode(code);

        if (!compile())
            System.err.println(getCompilerMessage());
    }
}