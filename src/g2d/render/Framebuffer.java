package g2d.render;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;

public class Framebuffer {
    public int id = 0;

    public Framebuffer() {
        id = glGenFramebuffers();
    }

    public void attachTexture(int attachment, Texture texture) {
        bind();
        glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, attachment, GL_TEXTURE_2D, texture.id, 0);
        unbind();
    }

    public void attachRenderBuffer(int attachment, Renderbuffer renderBuffer) {
        bind();
        glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, attachment, GL_RENDERBUFFER, renderBuffer.id);
        unbind();
    }

    public void bind() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, id);
    }

    public void unbind() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    public boolean isComplete() {
        bind();
        boolean result = glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE;
        unbind();

        return result;
    }
}