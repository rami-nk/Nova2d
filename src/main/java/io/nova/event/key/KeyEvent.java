package io.nova.event.key;

import io.nova.event.Event;

public abstract class KeyEvent extends Event {

    private int keyCode;

    public KeyEvent() { }

    public KeyEvent(int keyCode) {
        this.keyCode = keyCode;
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