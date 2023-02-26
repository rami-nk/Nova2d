package io.nova.core.window;

import io.nova.event.Event;

import java.util.function.Function;

public interface Window {

    void onUpdate();

    int getWidth();
    int getHeight();

    <T extends Event> void setEventCallback(Function<T, Boolean> eventCallback);
    void setVsync(boolean enabled);
    boolean isVsync();

    Object getNativeWindow();

    Window create(final WindowProps props);
}