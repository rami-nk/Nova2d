package io.nova.event.mouse;

import io.nova.core.codes.MouseCode;
import io.nova.event.EventType;

public class MouseButtonReleasedEvent extends MouseButtonEvent {

    public MouseButtonReleasedEvent() {
        super(MouseCode.BUTTON_8, EventType.MouseButtonReleased);
    }

    public MouseButtonReleasedEvent(MouseCode mouseCode) {
        super(mouseCode, EventType.MouseButtonReleased);
    }
}