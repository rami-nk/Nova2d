package io.nova.event.window;

import io.nova.event.Event;
import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class WindowResizeEvent extends Event {

    private final int width;
    private final int height;

    public WindowResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
        this.eventType = EventType.WindowResize;
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