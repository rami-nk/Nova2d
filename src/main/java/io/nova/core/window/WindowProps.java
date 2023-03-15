package io.nova.core.window;

public class WindowProps {

    private String title;
    private int width;
    private int height;

    public WindowProps() {
        this.title = "Nova2d Engine";
        this.width = 1300;
        this.height = 900;
    }

    public WindowProps(String title) {
        this();
        this.title = title;
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