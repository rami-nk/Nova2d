package io.nova.window;

import io.nova.core.window.EventCallback;
import io.nova.core.window.Window;
import io.nova.core.window.WindowProps;
import io.nova.event.Event;
import io.nova.event.key.KeyPressedEvent;
import io.nova.event.key.KeyReleasedEvent;
import io.nova.event.mouse.MouseButtonPressed;
import io.nova.event.mouse.MouseButtonReleasedEvent;
import io.nova.event.mouse.MouseMovedEvent;
import io.nova.event.mouse.MouseScrolledEvent;
import io.nova.event.window.WindowClosedEvent;
import io.nova.event.window.WindowResizeEvent;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.jni.JNINativeInterface;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Nova2dWindow implements Window {

    private WindowData windowData;
    private long windowDataPointer;
    private long glfwWindow;

    public Nova2dWindow(WindowProps props) {
        windowData = new WindowData();
        init(props);
    }

    private void init(WindowProps props) {
        windowData.setTitle(props.getTitle());
        windowData.setWidth(props.getWidth());
        windowData.setHeight(props.getHeight());

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
        glfwWindow = glfwCreateWindow(windowData.getWidth(), windowData.getHeight(), windowData.getTitle(), NULL, NULL);
        if (glfwWindow == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        windowDataPointer = JNINativeInterface.NewGlobalRef(windowData);
        glfwSetWindowUserPointer(glfwWindow, windowDataPointer);

        // Setup event callbacks
        glfwSetWindowSizeCallback(glfwWindow, (glfwWindowPointer, width, height) -> {
            var windowDataPointer = glfwGetWindowUserPointer(glfwWindowPointer);
            var data = (WindowData) MemoryUtil.memGlobalRefToObject(windowDataPointer);
            data.setWidth(width);
            data.setHeight(height);

            var event = new WindowResizeEvent(data.getWidth(), data.getHeight());
            data.getEventCallback().accept(event);
        });

        glfwSetWindowCloseCallback(glfwWindow, (glfwWindowPointer) -> {
            var windowDataPointer = glfwGetWindowUserPointer(glfwWindowPointer);
            var data = (WindowData) MemoryUtil.memGlobalRefToObject(windowDataPointer);

            var event = new WindowClosedEvent();
            data.getEventCallback().dispatch(event);
        });

        glfwSetKeyCallback(glfwWindow, (glfwWindowPointer, key, scancode, action, mods) -> {
            var windowDataPointer = glfwGetWindowUserPointer(glfwWindowPointer);
            var data = (WindowData) MemoryUtil.memGlobalRefToObject(windowDataPointer);

            Event event = null;
            switch (action) {
                case GLFW_PRESS -> event = new KeyPressedEvent(key);
                case GLFW_RELEASE -> event = new KeyReleasedEvent(key);
                case GLFW_REPEAT -> event = new KeyPressedEvent(key, true);
            }
            data.getEventCallback().dispatch(event);
        });

        glfwSetMouseButtonCallback(glfwWindow, (glfwWindowPointer, button, action, mods) -> {
            var windowDataPointer = glfwGetWindowUserPointer(glfwWindowPointer);
            var data = (WindowData) MemoryUtil.memGlobalRefToObject(windowDataPointer);

            Event event = null;
            switch (action) {
                case GLFW_PRESS -> event = new MouseButtonPressed(button);
                case GLFW_RELEASE -> event = new MouseButtonReleasedEvent(button);
            }
            data.getEventCallback().dispatch(event);
        });

        glfwSetScrollCallback(glfwWindow, (glfwWindowPointer, xOffset, yOffset) -> {
            var windowDataPointer = glfwGetWindowUserPointer(glfwWindowPointer);
            var data = (WindowData) MemoryUtil.memGlobalRefToObject(windowDataPointer);

            var event = new MouseScrolledEvent((float) xOffset, (float) yOffset);
            data.getEventCallback().dispatch(event);
        });

        glfwSetCursorPosCallback(glfwWindow, (glfwWindowPointer, xPos, yPos) -> {
            var windowDataPointer = glfwGetWindowUserPointer(glfwWindowPointer);
            var data = (WindowData) MemoryUtil.memGlobalRefToObject(windowDataPointer);

            var event = new MouseMovedEvent((float) xPos, (float) yPos);
            data.getEventCallback().dispatch(event);
        });

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

    @Override
    public void onUpdate() {
        glfwPollEvents();
        glfwSwapBuffers(glfwWindow);
    }

    @Override
    public void setEventCallback(EventCallback eventCallback) {
        this.windowData.setEventCallback(eventCallback);
    }

    @Override
    public boolean isVsync() {
        return windowData.isvSyncEnabled();
    }

    @Override
    public void setVsync(boolean enabled) {
        if (enabled) {
            glfwSwapInterval(1);
        } else {
            glfwSwapInterval(0);
        }
        windowData.setVSyncEnabled(enabled);
    }

    @Override
    public void shutdown() {
        JNINativeInterface.DeleteGlobalRef(windowDataPointer);
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
        return windowData.getWidth();
    }

    @Override
    public int getHeight() {
        return windowData.getHeight();
    }
}