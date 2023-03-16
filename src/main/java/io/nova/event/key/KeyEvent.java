package io.nova.event.key;

import io.nova.event.Event;
import io.nova.event.EventType;

public abstract class KeyEvent extends Event {

    private int keyCode;

    public KeyEvent() { }

    public KeyEvent(int keyCode, EventType eventType) {
        this.keyCode = keyCode;
        this.eventType = eventType;
    }

    public int getKeyCode() {
        return keyCode;
    }

    @Override
    public String toString() {
        return "KeyEvent{" +
                "keyCode=" + keyCode +
                '}';
    }
}