package io.nova.core.codes;


public enum MouseCode {

    BUTTON_1(0),
    BUTTON_2(1),
    BUTTON_3(2),
    BUTTON_4(3),
    BUTTON_5(4),
    BUTTON_6(5),
    BUTTON_7(6),
    BUTTON_8(7),
    BUTTON_LAST(BUTTON_8.getCode()),
    BUTTON_LEFT(BUTTON_1.getCode()),
    BUTTON_RIGHT(BUTTON_2.getCode()),
    BUTTON_MIDDLE(BUTTON_3.getCode());

    private final int code;

    MouseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}