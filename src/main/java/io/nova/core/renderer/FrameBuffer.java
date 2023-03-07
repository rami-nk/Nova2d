package io.nova.core.renderer;

public interface FrameBuffer {

    void invalidate();

    void bind();

    void unbind();

    void dispose();

    FrameBufferSpecification getSpecification();

    int getColorAttachmentRendererId();

    void resize(int width, int height);
}