package io.nova.core.renderer;

public interface GraphicsContext {
    void init(long windowHandle);
    void swapBuffers();
    long getWindowHandle();
}