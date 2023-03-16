package io.nova.event.window;

import io.nova.event.Event;
import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class WindowClosedEvent extends Event {

    public WindowClosedEvent() {
        this.eventType =  EventType.WindowClose;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Input.get() | EventCategory.Application.get();
    }
}