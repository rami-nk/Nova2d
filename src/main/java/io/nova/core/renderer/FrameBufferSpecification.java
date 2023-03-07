package io.nova.core.renderer;

public class FrameBufferSpecification {

    public int width;
    public int height;
    public boolean swapChainTarget;

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
}