package io.nova.event.key;

import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class KeyTypedEvent extends KeyEvent {

    public KeyTypedEvent() { }

    public KeyTypedEvent(int keyCode) {
        super(keyCode);
        this.eventType = EventType.KeyTyped;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Keyboard.get() | EventCategory.Input.get();
    }
}