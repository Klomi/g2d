package g2d.test;

import g2d.render.Primitive;
import g2d.render.Renderer;
import g2d.render.Texture;
import g2d.utils.ImageUtils;
import org.joml.Matrix4d;

import static org.lwjgl.opengl.GL11.*;

public class TextureTest {
    public TextureTest() {
        final Primitive prim = new Primitive(GL_TRIANGLES, 6);
        prim.posData.put(0).put(0);
        prim.posData.put(0).put(800);
        prim.posData.put(800).put(800);
        prim.posData.put(0).put(0);
        prim.posData.put(800).put(800);
        prim.posData.put(800).put(0);
        prim.colorData.put(1).put(1).put(1).put(1);
        prim.colorData.put(1).put(1).put(1).put(1);
        prim.colorData.put(1).put(1).put(1).put(1);
        prim.colorData.put(1).put(1).put(1).put(1);
        prim.colorData.put(1).put(1).put(1).put(1);
        prim.colorData.put(1).put(1).put(1).put(1);
        prim.texCoordData.put(0).put(0);
        prim.texCoordData.put(0).put(1);
        prim.texCoordData.put(1).put(1);
        prim.texCoordData.put(0).put(0);
        prim.texCoordData.put(1).put(1);
        prim.texCoordData.put(1).put(0);

        new Window(800, 800, "TextureTest") {
            public void init() {
                glEnable(GL_TEXTURE_2D);
                glEnable(GL_BLEND);
                Renderer.init();
                glViewport(0, 0, 800, 800);
                glClearColor(0, 0, 0, 1);
                Renderer.setMatrix(new Matrix4d().ortho(0, 800, 0, 800, 1, -1));

                Texture t = Texture.fromImage(ImageUtils.readImage("test.jpg"));
                prim.rs.tID = t.id;
            }

            public void renderLoop() {
                glClear(GL_COLOR_BUFFER_BIT);
                Renderer.push(prim);
                Renderer.flush();
            }
        }.run();
    }

    public static void main(String args[]) {
        new TextureTest();
    }
}