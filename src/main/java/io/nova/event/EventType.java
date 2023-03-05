package io.nova.event;

public enum EventType {
    None,
    WindowClose, WindowResize, WindowFocus, WindowMoved,
    KeyPressed, KeyReleased, KeyTyped,
    MouseButtonPressed, MouseButtonReleased, MouseMoved, MouseScrolled
}