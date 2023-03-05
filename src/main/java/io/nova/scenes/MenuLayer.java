package io.nova.scenes;

import imgui.ImGui;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.Renderer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MenuLayer extends Layer {

    private final Map<String, Supplier<Layer>> scenes;
    private Layer currentLayer;
    private Renderer renderer;

    public MenuLayer(Layer currentLayer) {
        this.currentLayer = currentLayer;
        this.scenes = new HashMap<>();
    }

    public MenuLayer() {
        this.scenes = new HashMap<>();
    }

    public <T extends Layer> void registerScene(String name, Class<T> clazz) {
        scenes.put(name, () -> {
            try {
                //                instance.start();
                return clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onUpdate(float deltaTime) {
        // TODO: Remove that from here
        renderer.setClearColor(0, 0, 0, 0);
        renderer.clear();
    }

    @Override
    public void onImGuiRender() {
        for (var scene : scenes.entrySet()) {
            if (ImGui.button(scene.getKey())) {
                currentLayer = scene.getValue().get();
                return;
            }
        }
    }

    public void setCurrentLayer(Layer currentLayer) {
        this.currentLayer = currentLayer;
    }

    public Layer getCurrentLayer() {
        return currentLayer;
    }
}