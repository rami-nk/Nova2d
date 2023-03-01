package io.nova.event.mouse;

import io.nova.event.EventType;

public class MouseButtonPressedEvent extends MouseButtonEvent {

    public MouseButtonPressedEvent() {
        super(0, EventType.MouseButtonPressed);
    }

    public MouseButtonPressedEvent(int mouseCode) {
        super(mouseCode, EventType.MouseButtonPressed);
    }
}