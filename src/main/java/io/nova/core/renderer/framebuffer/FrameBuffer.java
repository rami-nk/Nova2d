package io.nova.core.renderer.framebuffer;

public interface FrameBuffer {

    void invalidate();

    void bind();

    void unbind();

    void dispose();

    FrameBufferSpecification getSpecification();

    default int getColorAttachmentRendererId() {
        return getColorAttachmentRendererId(0);
    }

    int getColorAttachmentRendererId(int index);

    void resize(int width, int height);
}