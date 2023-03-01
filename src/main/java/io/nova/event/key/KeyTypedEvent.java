package io.nova.event.key;

import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class KeyTypedEvent extends KeyEvent {

    public KeyTypedEvent() {
        super(0, EventType.KeyTyped);
    }

    public KeyTypedEvent(int keyCode) {
        super(keyCode, EventType.KeyTyped);
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Keyboard.get() | EventCategory.Input.get();
    }
}