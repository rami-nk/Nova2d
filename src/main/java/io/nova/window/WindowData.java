package io.nova.window;

import io.nova.core.window.EventCallback;

class WindowData {
    private String title;
    private int width, height;
    private boolean vSyncEnabled;
    private EventCallback eventCallback;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isvSyncEnabled() {
        return vSyncEnabled;
    }

    public void setVSyncEnabled(boolean vSyncEnabled) {
        this.vSyncEnabled = vSyncEnabled;
    }

    public EventCallback getEventCallback() {
        return eventCallback;
    }

    public void setEventCallback(EventCallback eventCallback) {
        this.eventCallback = eventCallback;
    }

    @Override
    public String toString() {
        return "WindowData{" +
                "title='" + title + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", vSyncEnabled=" + vSyncEnabled +
                '}';
    }
}