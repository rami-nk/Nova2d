package io.nova.event.mouse;

import io.nova.event.Event;
import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class MouseScrolledEvent extends Event {

    private final float xOffset;
    private final float yOffset;

    public MouseScrolledEvent(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.eventType = EventType.MouseScrolled;
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Mouse.get() | EventCategory.Input.get();
    }

    @Override
    public String toString() {
        return "MouseScrolledEvent{" +
                "xOffset=" + xOffset +
                ", yOffset=" + yOffset +
                '}';
    }
}