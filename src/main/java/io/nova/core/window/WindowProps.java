package io.nova.core.window;

public class WindowProps {

    private final String name;
    private final int width;
    private final int height;

    public WindowProps() {
        this.name = "Nova2d Engine";
        this.width = 500;
        this.height = 500;
    }

    public WindowProps(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}