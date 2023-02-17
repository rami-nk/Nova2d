package io.nova;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Nova2dWindow {

    private static Nova2dWindow nova2dWindow;
    private final int width;
    private final int height;
    private final String title;
    private long glfwWindow;

    private Nova2dWindow(String title, int height, int width) {
        this.title = title;
        this.height = height;
        this.width = width;
    }

    public static Nova2dWindow getInstance() {
        if (Objects.isNull(nova2dWindow)) {
            nova2dWindow = new Nova2dWindow("Nova2d", 300, 300);
        }
        return nova2dWindow;
    }

    private static void printVersion() {
        System.out.printf("Hello LWJGL %s !%n", Version.getVersion());
    }

    private static void terminateGLFWAndFreeErrorCallback() {
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public static void main(String[] args) {
        Nova2dWindow.getInstance().run();
    }

    public void run() {
        printVersion();

        init();
        loop();

        freeWindowCallbacksAndDestroyWindow();
        terminateGLFWAndFreeErrorCallback();
    }

    private void init() {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::cursorPositionCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    private void loop() {
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(glfwWindow)) {

            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                System.out.println("Space is pressed");
            }

            glfwSwapBuffers(glfwWindow); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void freeWindowCallbacksAndDestroyWindow() {
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
    }
}