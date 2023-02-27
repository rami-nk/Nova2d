package io.nova.scenes;

import imgui.ImGui;
import io.nova.core.layer.Layer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;

public class ClearColorScene extends Layer {

    private final float[] color;

    public ClearColorScene() {
        color = new float[3];
    }

    @Override
    public void onUpdate() {
        // TODO: Remove that from here
        glClearColor(color[0], color[1], color[2], 0);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void onImGuiRender() {
        ImGui.colorEdit4("Clear color", color);
    }
}