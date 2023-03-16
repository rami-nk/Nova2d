package io.nova.window;

import io.nova.core.application.Application;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    public static boolean isKeyPressed(int keycode) {
        var window = Application.getWindow().getNativeWindow();
        var state = glfwGetKey(window, keycode);
        return state == GLFW_PRESS;
    }

    public static boolean isMouseButtonPressed(int mouseButton) {
        var window = Application.getWindow().getNativeWindow();
        var state = glfwGetMouseButton(window, mouseButton);
        return state == GLFW_PRESS;
    }

    public static Vector2f getMousePosition() {
        var window = Application.getWindow().getNativeWindow();
        var xPos = BufferUtils.createDoubleBuffer(1);
        var yPos = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, xPos, yPos);
        return new Vector2f((float) xPos.get(), (float) yPos.get());
    }

    public static float getMouseX() {
        return getMousePosition().x;
    }

    public static float getMouseY() {
        return getMousePosition().y;
    }
}