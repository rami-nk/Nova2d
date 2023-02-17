package io.nova;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class KeyListener {

    private static final int KEYBOARD_BUTTON_NUMBER = 350;
    private static KeyListener keyListener;

    private final boolean[] keyPressed;

    private KeyListener() {
        keyPressed = new boolean[KEYBOARD_BUTTON_NUMBER];
    }

    public static KeyListener getInstance() {
        if (Objects.isNull(keyListener)) {
            keyListener = new KeyListener();
        }
        return keyListener;
    }

    public static void keyCallback(long glfwWindow, int keyCode, int scanCode, int action, int mods) {
        getInstance().keyPressed[keyCode] = action == GLFW_PRESS;
    }

    public static boolean isKeyPressed(int keyCode) {
        return getInstance().keyPressed[keyCode];
    }
}