package g2d.render;

import static org.lwjgl.opengl.GL30.*;

public class Renderbuffer {
    public int id = 0;

    public Renderbuffer(int id) {
        this.id = id;
    }

    public Renderbuffer(int format, int width, int height) {
        this(glGenRenderbuffers());
        bind();
        glRenderbufferStorage(GL_RENDERBUFFER, format, width, height);
        unbind();
    }

    public void bind() {
        glBindRenderbuffer(GL_RENDERBUFFER, id);
    }

    public void unbind() {
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }
}