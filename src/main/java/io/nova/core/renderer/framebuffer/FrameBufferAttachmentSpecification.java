package io.nova.core.renderer.framebuffer;

import java.util.List;

public class FrameBufferAttachmentSpecification {

    private List<FrameBufferTextureSpecification> attachments;

    public FrameBufferAttachmentSpecification() {
    }

    public FrameBufferAttachmentSpecification(List<FrameBufferTextureSpecification> attachments) {
        this.attachments = attachments;
    }
}