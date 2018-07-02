package g2d.test;

import g2d.Image;
import g2d.Interpolator;
import g2d.Sprite;
import g2d.Transition;
import g2d.render.Renderer;
import g2d.render.Texture;
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
                new Transition.Builder()
                        .timeOffset(0)
                        .duration(1.5)
                        .interpolator(Interpolator.EASEINOUT_BACK)
                        .change(Sprite.InterpolatableProperties.X, 0, 800)
                        .build()
                        .applyTo(s1);
                s1.setPosition(0, 400);
                s1.setScale(8, 8);

                s2 = new Sprite(img);
                s2.setPosition(400, 400);
                s2.setScale(8, 8);
            }

            public void renderLoop() {
                glClear(GL_COLOR_BUFFER_BIT);
                if (time % 120 == 0) {
                    s2.clearTransitions();
                    new Transition.Builder()
                            .timeOffset(time / 60.0)
                            .duration(2)
                            .interpolator(Interpolator.EASEOUT_QUAD)
                            .change(Sprite.InterpolatableProperties.ROTATION_ALPHA, 0, Math.PI * 2)
                            .change(Sprite.InterpolatableProperties.SCALE_X, 8, 16)
                            .change(Sprite.InterpolatableProperties.SCALE_Y, 8, 16)
                            .change(Sprite.InterpolatableProperties.ALPHA, 1, 0)
                            .build()
                            .applyTo(s2);
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