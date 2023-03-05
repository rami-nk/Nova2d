package io.nova.event.key;

import io.nova.event.EventCategory;
import io.nova.event.EventType;

public class KeyPressedEvent extends KeyEvent {

    private boolean isRepeat;

    public KeyPressedEvent() {
        super(0, EventType.KeyPressed);
    }

    public KeyPressedEvent(int keyCode) {
        this(keyCode, false);
    }

    public KeyPressedEvent(int keyCode, boolean isRepeat) {
        super(keyCode, EventType.KeyPressed);
        this.isRepeat = isRepeat;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Keyboard.get() | EventCategory.Input.get();
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    @Override
    public String toString() {
        return "KeyPressedEvent{" +
                "isRepeat=" + isRepeat +
                '}';
    }
}