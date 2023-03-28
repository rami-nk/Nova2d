package io.nova.event.key;

import io.nova.core.codes.KeyCode;
import io.nova.event.Event;
import io.nova.event.EventType;

public abstract class KeyEvent extends Event {

    private KeyCode keyCode;

    public KeyEvent() {}

    public KeyEvent(KeyCode keyCode, EventType eventType) {
        this.keyCode = keyCode;
        this.eventType = eventType;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }

    @Override
    public String toString() {
        return "KeyEvent{" +
                "keyCode=" + keyCode +
                '}';
    }
}