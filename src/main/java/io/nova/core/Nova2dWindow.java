package io.nova.core;

import io.nova.core.listener.KeyListener;
import io.nova.core.listener.MouseListener;
import io.nova.core.window.Window;
import io.nova.core.window.WindowProps;
import io.nova.event.Event;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import java.util.Objects;
import java.util.function.Function;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Nova2dWindow implements Window {

    private String title;
    private int width;
    private int height;
    private boolean vsyncEnabled;

    private long glfwWindow;

    @Override
    public void onUpdate() {
        glfwPollEvents();
        glfwSwapBuffers(glfwWindow);
    }

    @Override
    public <T extends Event> void setEventCallback(Function<T, Boolean> eventCallback) {

    }

    @Override
    public void setVsync(boolean enabled) {
        if (enabled) {
            glfwSwapInterval(1);
        } else {
            glfwSwapInterval(0);
        }
        vsyncEnabled = enabled;
    }

    @Override
    public boolean isVsync() {
        return vsyncEnabled;
    }

    @Override
    public void shutdown() {
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    @Override
    public long getNativeWindow() {
        return glfwWindow;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public Nova2dWindow(WindowProps props) {
        init(props);
    }

    private void init(WindowProps props) {
        this.title = props.getName();
        this.width = props.getWidth();
        this.height = props.getHeight();

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
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

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
        setVsync(true);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();


        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        GLUtil.setupDebugMessageCallback();
    }
}