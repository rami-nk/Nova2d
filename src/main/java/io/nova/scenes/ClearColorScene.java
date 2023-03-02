package io.nova.scenes;

import imgui.ImGui;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.Renderer;

public class ClearColorScene extends Layer {

    private final float[] color;
    private final Renderer renderer;

    public ClearColorScene() {
        renderer = Renderer.create();
        color = new float[3];
    }

    @Override
    public void onUpdate(float deltaTime) {
        renderer.setClearColor(color[0], color[1], color[2], 0);
        renderer.clear();
    }

    @Override
    public void onImGuiRender() {
        ImGui.colorEdit4("Clear color", color);
    }
}