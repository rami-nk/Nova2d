package io.nova.event.key;

import io.nova.core.codes.KeyCode;
import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class KeyReleasedEvent extends KeyEvent {

    public KeyReleasedEvent() {
        super(KeyCode.KEY_UNKNOWN, EventType.KeyReleased);
    }

    public KeyReleasedEvent(KeyCode keyCode) {
        super(keyCode, EventType.KeyReleased);
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Keyboard.get();
    }
}