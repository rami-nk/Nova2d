package io.nova.core.renderer;

public class FrameBufferSpecification {

    private int width;
    private int height;
    private boolean swapChainTarget;

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

    public boolean isSwapChainTarget() {
        return swapChainTarget;
    }

    public void setSwapChainTarget(boolean swapChainTarget) {
        this.swapChainTarget = swapChainTarget;
    }
}