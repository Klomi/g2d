package g2d.test;

import g2d.render.Framebuffer;
import g2d.render.Primitive;
import g2d.render.Renderer;
import g2d.render.Texture;
import org.joml.Matrix4d;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;

public class FramebufferTest {
    public FramebufferTest() {
        final Primitive prim = new Primitive(GL_TRIANGLES, 3);
        prim.rs.tID = 1;
        prim.posData.put(0).put(0);
        prim.posData.put(0).put(400);
        prim.posData.put(400).put(400);
        prim.colorData.put(1).put(0).put(0).put(1);
        prim.colorData.put(0).put(1).put(0).put(1);
        prim.colorData.put(0).put(0).put(1).put(1);
        prim.texCoordData.put(0).put(0);
        prim.texCoordData.put(0).put(0);
        prim.texCoordData.put(0).put(0);

        final Primitive target = new Primitive(GL_TRIANGLES, 6);
        target.posData.put(400).put(400);
        target.posData.put(400).put(800);
        target.posData.put(800).put(800);
        target.posData.put(400).put(400);
        target.posData.put(800).put(800);
        target.posData.put(800).put(400);
        target.colorData.put(1).put(1).put(1).put(1);
        target.colorData.put(1).put(1).put(1).put(1);
        target.colorData.put(1).put(1).put(1).put(1);
        target.colorData.put(1).put(1).put(1).put(1);
        target.colorData.put(1).put(1).put(1).put(1);
        target.colorData.put(1).put(1).put(1).put(1);
        target.texCoordData.put(0).put(0);
        target.texCoordData.put(0).put(1);
        target.texCoordData.put(1).put(1);
        target.texCoordData.put(0).put(0);
        target.texCoordData.put(1).put(1);
        target.texCoordData.put(1).put(0);

        final Holder<Framebuffer> buf = new Holder<>();
        new Window(800, 800, "TextureTest") {
            public void init() {
                glEnable(GL_TEXTURE_2D);
                glEnable(GL_BLEND);
                Renderer.init();
                glViewport(0, 0, 800, 800);
                glClearColor(0, 0, 0, 1);
                Renderer.setMatrix(new Matrix4d().ortho(0, 800, 0, 800, 1, -1));

                Texture t = new Texture(400, 400);
                target.rs.tID = t.id;
                buf.val = new Framebuffer();
                buf.val.attachTexture(GL_COLOR_ATTACHMENT0, t);
            }

            public void renderLoop() {
                buf.val.bind();
                glViewport(0, 0, 400, 400);
                Renderer.setMatrix(new Matrix4d().ortho(0, 400, 0, 400, 1, -1));
                glClear(GL_COLOR_BUFFER_BIT);
                Renderer.push(prim);
                Renderer.flush();

                buf.val.unbind();
                glViewport(0, 0, 800, 800);
                Renderer.setMatrix(new Matrix4d().ortho(0, 800, 0, 800, 1, -1));
                glClear(GL_COLOR_BUFFER_BIT);
                Renderer.push(target);
                Renderer.flush();
            }
        }.run();
    }

    static class Holder<T> {
        T val;
    }

    public static void main(String args[]) {
        new FramebufferTest();
    }
}