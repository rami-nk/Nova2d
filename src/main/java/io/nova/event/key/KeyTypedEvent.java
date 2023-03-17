package io.nova.event.key;

import io.nova.core.codes.KeyCode;
import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class KeyTypedEvent extends KeyEvent {

    public KeyTypedEvent() {
        super(KeyCode.KEY_UNKNOWN, EventType.KeyTyped);
    }

    public KeyTypedEvent(KeyCode keyCode) {
        super(keyCode, EventType.KeyTyped);
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Keyboard.get() | EventCategory.Input.get();
    }
}