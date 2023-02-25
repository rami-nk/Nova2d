package io.nova.core.scene;

import imgui.ImGui;
import io.nova.core.Nova2dWindow;

public class ClearColorScene extends Scene {

    private final float[] color;

    ClearColorScene() {
        color = new float[3];
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        Nova2dWindow.getInstance().changeColorTo(color[0], color[1], color[2]);
    }

    @Override
    public void imGuiRender() {
        ImGui.colorEdit4("Clear color", color);
    }
}