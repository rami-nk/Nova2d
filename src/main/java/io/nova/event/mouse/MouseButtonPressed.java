package io.nova.event.mouse;

import io.nova.event.EventType;

public class MouseButtonPressed extends MouseButtonEvent {

    public MouseButtonPressed(int mouseCode) {
        super(mouseCode);
        this.eventType = EventType.MouseButtonPressed;
    }
}