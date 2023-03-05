package io.nova.event;

public abstract class Event {

    protected boolean isHandled;
    protected EventType eventType;

    public EventType getEventType() {
        return eventType;
    }

    public String getName() {
        return eventType.name();
    }

    public abstract int GetCategoryFlags();

    public String toString() {
        return getName();
    }

    public boolean isHandled() {
        return isHandled;
    }

    public void setHandled(boolean handled) {
        isHandled = handled;
    }
}
