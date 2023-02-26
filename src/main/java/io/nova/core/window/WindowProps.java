package io.nova.core.window;

public class WindowProps {

    private final String name;
    private final int width;
    private final int height;

    public WindowProps() {
        this.name = "Nova2d Engine";
        this.width = 1600;
        this.height = 900;
    }

    public WindowProps(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }
}