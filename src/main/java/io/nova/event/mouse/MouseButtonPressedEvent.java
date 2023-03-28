package io.nova.event.mouse;

import io.nova.core.codes.MouseCode;
import io.nova.event.EventType;

public class MouseButtonPressedEvent extends MouseButtonEvent {

    public MouseButtonPressedEvent() {
        super(MouseCode.BUTTON_8, EventType.MouseButtonPressed);
    }

    public MouseButtonPressedEvent(MouseCode mouseCode) {
        super(mouseCode, EventType.MouseButtonPressed);
    }
}