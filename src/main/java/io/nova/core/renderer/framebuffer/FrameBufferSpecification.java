package io.nova.core.renderer.framebuffer;

import java.util.List;

public class FrameBufferSpecification {

    private int width, height;
    private boolean swapChainTarget;

    private FrameBufferAttachmentSpecification attachments;

    public FrameBufferSpecification() {
    }

    public FrameBufferSpecification(int width, int height, boolean swapChainTarget) {
        this.width = width;
        this.height = height;
        this.swapChainTarget = swapChainTarget;
    }

    public FrameBufferSpecification(int width, int height) {
        this(width, height, false);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public FrameBufferAttachmentSpecification getAttachments() {
        return attachments;
    }

    public void setAttachments(FrameBufferAttachmentSpecification attachments) {
        this.attachments = attachments;
    }

    public void setAttachments(FrameBufferTextureSpecification... attachments) {
        this.attachments = new FrameBufferAttachmentSpecification(List.of(attachments));
    }

    public boolean isSwapChainTarget() {
        return swapChainTarget;
    }

    public void setSwapChainTarget(boolean swapChainTarget) {
        this.swapChainTarget = swapChainTarget;
    }
}