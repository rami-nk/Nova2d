package io.nova.event;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class EventDispatcher {

    private final Event event;

    public EventDispatcher(Event event) {
        this.event = event;
    }

    // TODO: refactor this function
    public <T extends Event> boolean dispatch(final Class<T> clazz, final Function<T, Boolean> func) {
        T e;
        try {
            e = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        if (event.getEventType() == e.getEventType()) {
            event.setHandled(event.isHandled() || func.apply((T)event));
            return true;
        }
        return false;
    }
}