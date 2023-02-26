package io.nova.event;

public enum EventCategory {
    None(0),
    Application(1),
    Input(2),
    Keyboard(3),
    Mouse(4),
    MouseButton(5);

    private final int bit;

    EventCategory(int bit) {
        this.bit = BIT(bit);
    }

    private static int BIT(int num) {
        return (1 << (num - 1));
    }

    public int get() {
        return bit;
    }
}