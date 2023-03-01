package io.nova.event.mouse;

import io.nova.event.Event;
import io.nova.event.EventCategory;
import io.nova.event.EventType;

public abstract class MouseButtonEvent extends Event {

    private int mouseCode;

    public MouseButtonEvent() { }

    public MouseButtonEvent(int mouseCode, EventType eventType) {
        this.mouseCode = mouseCode;
        this.eventType = eventType;
    }

    public int getMouseCode() {
        return mouseCode;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.MouseButton.get() | EventCategory.Mouse.get() | EventCategory.Input.get();
    }

    @Override
    public String toString() {
        return "MouseButtonEvent{" +
                "mouseCode=" + mouseCode +
                '}';
    }
}