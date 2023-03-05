package io.nova.event.mouse;

import io.nova.event.EventType;

public class MouseButtonReleasedEvent extends MouseButtonEvent {

    public MouseButtonReleasedEvent() {
        super(0, EventType.MouseButtonReleased);
    }
    public MouseButtonReleasedEvent(int mouseCode) {
        super(mouseCode, EventType.MouseButtonReleased);
    }
}