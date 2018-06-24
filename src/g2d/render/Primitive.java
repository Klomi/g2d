package g2d.render;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Primitive {
    public RenderState rs = new RenderState();
    public int vertexCount = 0;

    public FloatBuffer posData = null;
    public FloatBuffer colorData = null;
    public FloatBuffer texCoordData = null;

    public Primitive(int type, int vertexCount) {
        this.rs.primType = type;
        this.vertexCount = vertexCount;

        // Indirect buffers
        posData = ByteBuffer.allocate(vertexCount * 2 * 4).asFloatBuffer();
        colorData = ByteBuffer.allocate(vertexCount * 4 * 4).asFloatBuffer();
        texCoordData = ByteBuffer.allocate(vertexCount * 2 * 4).asFloatBuffer();
    }

    public void flipBuffers() {
        posData.flip();
        colorData.flip();
        texCoordData.flip();
    }
}