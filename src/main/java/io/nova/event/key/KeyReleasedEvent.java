package io.nova.event.key;

import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class KeyReleasedEvent extends KeyEvent {

    public KeyReleasedEvent() {
        super(0, EventType.KeyReleased);
    }

    public KeyReleasedEvent(int keyCode) {
        super(keyCode, EventType.KeyReleased);
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Keyboard.get();
    }
}