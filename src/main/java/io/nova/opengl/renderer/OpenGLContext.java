package io.nova.opengl.renderer;

import io.nova.core.renderer.GraphicsContext;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

public class OpenGLContext implements GraphicsContext {

    private long windowHandle;

    public OpenGLContext() {
    }

    @Override
    public void init(long windowHandle) {
        this.windowHandle = windowHandle;

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        GLUtil.setupDebugMessageCallback();
    }

    @Override
    public void swapBuffers() {
        glfwSwapBuffers(this.windowHandle);
    }

    @Override
    public long getWindowHandle() {
        return this.windowHandle;
    }
}