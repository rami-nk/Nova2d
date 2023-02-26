package io.nova.core.window;

public class WindowProps {

    private final String title;
    private final int width;
    private final int height;

    public WindowProps() {
        this.title = "Nova2d Engine";
        this.width = 500;
        this.height = 500;
    }

    public WindowProps(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}