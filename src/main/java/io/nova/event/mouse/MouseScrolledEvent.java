package io.nova.event.mouse;

import io.nova.event.Event;
import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class MouseScrolledEvent extends Event {

    private float xOffset;
    private float yOffset;

    public MouseScrolledEvent() {
        this.eventType = EventType.MouseScrolled;
    }

    public MouseScrolledEvent(float xOffset, float yOffset) {
        this();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
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