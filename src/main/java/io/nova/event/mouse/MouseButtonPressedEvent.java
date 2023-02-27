package io.nova.event.mouse;

import io.nova.event.EventType;

public class MouseButtonPressedEvent extends MouseButtonEvent {

    public MouseButtonPressedEvent() { }

    public MouseButtonPressedEvent(int mouseCode) {
        super(mouseCode);
        this.eventType = EventType.MouseButtonPressed;
    }
}