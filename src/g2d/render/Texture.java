package g2d.render;

import g2d.utils.ImageUtils;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Texture {
    public int id = 0;
    public int width = 0, height = 0;

    public Texture(int id) {
        this.id = id;
    }

    public Texture(int width, int height) {
        this(glGenTextures());
        this.width = width;
        this.height = height;

        bind();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        unbind();
    }

    public void setParameter(int paramName, int paramValue) {
        bind();
        glTexParameteri(GL_TEXTURE_2D, paramName, paramValue);
        unbind();
    }

    public void setData(ByteBuffer data) {
        bind();
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, data);
        unbind();
    }

    public static Texture fromImage(BufferedImage img) {
        Texture t = new Texture(img.getWidth(), img.getHeight());
        byte data[] = ImageUtils.readRGBAPixels(ImageUtils.flipImageVertically(img));
        ByteBuffer buf = BufferUtils.createByteBuffer(data.length).put(data);
        buf.flip();
        t.setData(buf);
        return t;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}