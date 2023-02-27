package io.nova.event.mouse;

public class MouseButtonReleasedEvent extends MouseButtonEvent {

    public MouseButtonReleasedEvent() { }
    public MouseButtonReleasedEvent(int mouseCode) {
        super(mouseCode);
    }
}