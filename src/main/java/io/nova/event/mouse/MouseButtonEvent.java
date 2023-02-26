package io.nova.event.mouse;

import io.nova.event.Event;
import io.nova.event.EventCategory;

public abstract class MouseButtonEvent extends Event {

    private final int mouseCode;

    public MouseButtonEvent(int mouseCode) {
        this.mouseCode = mouseCode;
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