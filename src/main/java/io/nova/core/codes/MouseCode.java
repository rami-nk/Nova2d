package io.nova.core.codes;


import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Integer, MouseCode> MOUSECODE_MAP = new HashMap<>();

    static {
        for (MouseCode mouseCode : MouseCode.values()) {
            MOUSECODE_MAP.put(mouseCode.getCode(), mouseCode);
        }
    }

    private final int code;

    MouseCode(int code) {
        this.code = code;
    }

    public static MouseCode getMouseCode(int key) {
        MouseCode mouseCode = MOUSECODE_MAP.get(key);
        return (mouseCode != null) ? mouseCode : MouseCode.BUTTON_8;
    }

    public int getCode() {
        return code;
    }
}