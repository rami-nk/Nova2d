package io.nova.event.mouse;

import io.nova.event.Event;
import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class MouseMovedEvent extends Event {

    private float mouseX;
    private float mouseY;

    public MouseMovedEvent() {
       this.eventType = EventType.MouseMoved;
    }

    public MouseMovedEvent(float x, float y) {
        this();
        this.mouseX = x;
        this.mouseY = y;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Mouse.get() | EventCategory.Input.get();
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    @Override
    public String toString() {
        return "MouseMovedEvent{" +
                "mouseX=" + mouseX +
                ", mouseY=" + mouseY +
                '}';
    }
}