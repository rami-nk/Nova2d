package io.nova.core.renderer;

public interface FrameBuffer {

    void invalidate();

    void bind();

    void unbind();

    FrameBufferSpecification getSpecification();

    int getColorAttachmentRendererId();
}