package io.nova.core.listener;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class MouseListener {

    private static final int MOUSE_BUTTON_NUMBER = 3;

    private static MouseListener mouseListener;
    private double scrollX, scrollY;
    private double xPos, yPos, prevX, prevY;
    private final boolean[] mouseButtonPressed;
    private boolean isDragging;

    private MouseListener() {
        scrollX = 0.0;
        scrollY = 0.0;
        xPos = 0.0;
        yPos = 0.0;
        prevX = 0.0;
        prevY = 0.0;
        mouseButtonPressed = new boolean[MOUSE_BUTTON_NUMBER];
    }

    public static MouseListener getInstance() {
        if (Objects.isNull(mouseListener)) {
            mouseListener = new MouseListener();
        }
        return mouseListener;
    }

    public static void cursorPositionCallback(long glfWindow, double xPos, double yPos) {
        getInstance().prevX = getInstance().xPos;
        getInstance().prevY = getInstance().yPos;
        getInstance().xPos = xPos;
        getInstance().yPos = yPos;
        getInstance().isDragging = getInstance().mouseButtonPressed[0] ||
                getInstance().mouseButtonPressed[1] ||
                getInstance().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long glfWindow, int button, int action, int mods) {
        // Ignore mouse button click if more than three mouse buttons
        if (button >= MOUSE_BUTTON_NUMBER) return;

        if (action == GLFW_PRESS) {
            getInstance().mouseButtonPressed[button] = true;
        } else {
            getInstance().mouseButtonPressed[button] = false;
            getInstance().isDragging = false;
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        getInstance().scrollX = xOffset;
        getInstance().scrollY = yOffset;
    }

    public static void endFrame() {
        getInstance().scrollX = 0.0;
        getInstance().scrollY = 0.0;
        getInstance().prevX = getInstance().xPos;
        getInstance().prevY = getInstance().yPos;
    }

    public static boolean isMouseButtonPressed(int button) {
        return button < MOUSE_BUTTON_NUMBER && getInstance().mouseButtonPressed[button];
    }

    public static double getScrollX() {
        return getInstance().scrollX;
    }

    public static double getScrollY() {
        return getInstance().scrollY;
    }

    public static double getXPos() {
        return getInstance().xPos;
    }

    public static double getYPos() {
        return getInstance().yPos;
    }

    public static double getDx() {
        return getInstance().prevX - getInstance().xPos;
    }

    public static double getDy() {
        return getInstance().prevY - getInstance().yPos;
    }

    public static boolean isDragging() {
        return getInstance().isDragging;
    }
}