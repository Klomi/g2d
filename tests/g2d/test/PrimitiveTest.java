package g2d.test;

import g2d.render.BlendMode;
import g2d.render.Primitive;
import g2d.render.Renderer;
import org.joml.Matrix4d;

import static org.lwjgl.opengl.GL11.*;

public class PrimitiveTest {
    public PrimitiveTest() {
        final Primitive prim1 = new Primitive(GL_TRIANGLES, 3);
        prim1.rs.blendMode = BlendMode.ADD;
        prim1.rs.tID = 1;
        prim1.posData.put(0).put(0);
        prim1.posData.put(400).put(400);
        prim1.posData.put(0).put(800);
        prim1.colorData.put(1).put(0).put(0).put(1);
        prim1.colorData.put(0).put(1).put(0).put(1);
        prim1.colorData.put(0).put(0).put(1).put(1);
        prim1.texCoordData.put(0).put(0);
        prim1.texCoordData.put(0).put(0);
        prim1.texCoordData.put(0).put(0);

        final Primitive prim2 = new Primitive(GL_TRIANGLE_FAN, 38);
        prim2.rs.tID = 1;
        prim2.posData.put(400).put(400);
        prim2.colorData.put(1).put(1).put(1).put(1);
        prim2.texCoordData.put(0).put(0);
        for (int i = 0; i <= 36; i++) {
            double angle = Math.PI * 2 * i / 36;
            prim2.posData.put((float) (200 * Math.cos(angle) + 400)).put((float) (200 * Math.sin(angle) + 400));
            prim2.colorData.put(0).put(1).put(1).put(0.1f);
            prim2.texCoordData.put(0).put(0);
        }

        new Window(800, 800, "PrimitiveTest") {
            public void init() {
                Renderer.init();
                glEnable(GL_BLEND);
                glViewport(0, 0, 800, 800);
                glClearColor(0, 0, 0, 1);
                Renderer.setMatrix(new Matrix4d().ortho(0, 800, 0, 800, 1, -1));
            }

            public void renderLoop() {
                glClear(GL_COLOR_BUFFER_BIT);
                Renderer.push(prim1);
                Renderer.push(prim2);
                Renderer.flush();
            }
        }.run();
    }

    public static void main(String args[]) {
        new PrimitiveTest();
    }
}