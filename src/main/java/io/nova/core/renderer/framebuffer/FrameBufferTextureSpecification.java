package io.nova.core.renderer.framebuffer;

public class FrameBufferTextureSpecification {

    private final FrameBufferTextureFormat format;

    public FrameBufferTextureSpecification() {
        this.format = FrameBufferTextureFormat.NONE;
    }

    public FrameBufferTextureSpecification(FrameBufferTextureFormat format) {
        this.format = format;
    }

    public FrameBufferTextureFormat getFormat() {
        return format;
    }
}