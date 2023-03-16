package io.nova.event.window;

import io.nova.event.Event;
import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class WindowResizeEvent extends Event {

    private int width;
    private int height;

    public WindowResizeEvent() {
        this.eventType = EventType.WindowResize;
    }

    public WindowResizeEvent(int width, int height) {
        this();
        this.width = width;
        this.height = height;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Application.get();
    }

    @Override
    public String toString() {
        return "WindowResizeEvent{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}