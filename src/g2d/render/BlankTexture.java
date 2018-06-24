package g2d.render;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class BlankTexture extends Texture {
    public BlankTexture() {
        super(1, 1);
        ByteBuffer buf = BufferUtils.createByteBuffer(4);
        buf.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
        buf.flip();
        setData(buf);
    }
}