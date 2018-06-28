package g2d.test;

import g2d.Image;
import g2d.Sprite;
import g2d.render.Renderer;
import g2d.render.Texture;
import g2d.transitions.ColorTransition;
import g2d.transitions.RotateTransition;
import g2d.transitions.ScaleTransition;
import g2d.transitions.TranslateTransition;
import g2d.utils.ImageUtils;
import org.joml.Matrix4d;

import static org.lwjgl.opengl.GL11.*;

public class TransitionTest {
    Texture t1;
    Image img;
    Sprite s1, s2;
    int time = 0;

    public TransitionTest() {
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
                s1.addTransition(TranslateTransition.translateX(0, 3, 400, 600));
                s1.x = 400;
                s1.y = 400;
                s1.sx = 8;
                s1.sy = 8;

                s2 = new Sprite(img);
                s2.x = 400;
                s2.y = 400;
                s2.sx = 8;
                s2.sy = 8;
            }

            public void renderLoop() {
                glClear(GL_COLOR_BUFFER_BIT);
                if (time % 60 == 0) {
                    s2.clearTransitions();
                    s2.addTransition(RotateTransition.rotateZ(time / 60.0, 1, 0, Math.PI * 2));
                    s2.addTransition(ScaleTransition.scaleXY(time / 60.0, 1, new double[]{8, 8}, new double[]{16, 16}));
                    s2.addTransition(ColorTransition.alpha(time / 60.0, 1, 1, 0));
                }

                s1.render(time / 60.0);
                s2.render(time / 60.0);
                Renderer.flush();

                time++;
            }
        }.run();
    }

    public static void main(String args[]) {
        new TransitionTest();
    }
}