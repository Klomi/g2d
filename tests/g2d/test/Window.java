package g2d.test;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private long window = 0;
    private GLFWErrorCallback errorCallback = null;
    private GLFWKeyCallback keyCallback = null;

    public Window(int width, int height, String title) {
        errorCallback = GLFWErrorCallback.createPrint(System.err);
        glfwSetErrorCallback(errorCallback);
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                    glfwSetWindowShouldClose(Window.this.window, true);
                }
            }
        };

        if (!glfwInit()) {
            System.err.println("Failed to init GLFW");
            return;
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        window = glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            System.out.println("Failed to create window");
            return;
        }
        glfwSetKeyCallback(window, keyCallback);
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2
        );

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(1);
        init();
    }

    public void run() {
        while (!glfwWindowShouldClose(window)) {
            renderLoop();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        glfwDestroyWindow(window);
        glfwTerminate();
        errorCallback.free();
        keyCallback.free();
    }

    public void init() {

    }

    public void renderLoop() {

    }
}