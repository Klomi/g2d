package g2d.render.shaders;

public class DefaultShaderProgram extends ShaderProgram {
    public DefaultShaderProgram() {
        Shader vertexShader = new DefaultVertexShader();
        Shader fragmentShader = new DefaultFragmentShader();
        attachShader(vertexShader, fragmentShader);
        if (!linkProgram())
            System.err.println(getLinkerMessage());
    }

    public void bind() {
        super.bind();
        varLocations.put("in_pos", getAttributeLocation("in_pos"));
        varLocations.put("in_color", getAttributeLocation("in_color"));
        varLocations.put("in_texCoord", getAttributeLocation("in_texCoord"));
        varLocations.put("matrix", getUniformLocation("matrix"));
    }
}