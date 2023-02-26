package io.nova.core.window;

public interface Window {

    void onUpdate();
    int getWidth();
    int getHeight();
    void setEventCallback(EventCallback eventCallback);
    void setVsync(boolean enabled);
    boolean isVsync();
    void shutdown();
    long getNativeWindow();
}