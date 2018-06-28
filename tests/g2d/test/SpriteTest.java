package g2d.test;

import g2d.Image;
import g2d.Sprite;
import g2d.render.BlendMode;
import g2d.render.Renderer;
import g2d.render.Texture;
import g2d.utils.ImageUtils;
import org.joml.Matrix4d;

import static org.lwjgl.opengl.GL11.*;

public class SpriteTest {
    Texture t1;
    Image img;
    Sprite s1, s2, s3;

    public SpriteTest() {
        new Window(800, 800, "SpriteTest") {
            public void init() {
                glEnable(GL_TEXTURE_2D);
                glEnable(GL_BLEND);
                Renderer.init();
                glViewport(0, 0, 800, 800);
                glClearColor(0, 0, 0, 1);
                Renderer.setMatrix(new Matrix4d().ortho(0, 800, 0, 800, 1, -1));

                t1 = Texture.fromImage(ImageUtils.readImage("test.png"));
                img = new Image(t1);
                img.setX(32);
                img.setWidth(32);
                img.setHeight(32);

                s1 = new Sprite(img);
                s1.x = 400;
                s1.y = 400;
                s1.color = new double[]{1, 0.5, 0.5, 1};
                s1.sx = 8;
                s1.sy = 8;

                s2 = new Sprite(img);
                s2.blendMode = BlendMode.ADD;
                s2.rot[0] = Math.PI * 2 / 3;
                s2.x = 400;
                s2.y = 400;
                s2.color = new double[]{0.5, 1, 0.5, 1};
                s2.sx = 8;
                s2.sy = 8;

                s3 = new Sprite(img);
                s3.blendMode = BlendMode.ADD;
                s3.rot[0] = -Math.PI * 2 / 3;
                s3.x = 400;
                s3.y = 400;
                s3.color = new double[]{0.5, 0.5, 1, 1};
                s3.sx = 8;
                s3.sy = 8;
            }

            public void renderLoop() {
                glClear(GL_COLOR_BUFFER_BIT);
                s1.render(0);
                s2.render(0);
                s3.render(0);
                Renderer.flush();
            }
        }.run();
    }

    public static void main(String args[]) {
        new SpriteTest();
    }
}