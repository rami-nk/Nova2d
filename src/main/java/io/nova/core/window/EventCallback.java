package io.nova.core.window;

import io.nova.event.Event;

import java.util.function.Consumer;

public interface EventCallback extends Consumer<Event> {

    default void dispatch(Event t) {
        accept(t);
    }
}
