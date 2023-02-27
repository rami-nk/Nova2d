package io.nova.scenes;

import imgui.ImGui;
import io.nova.core.layer.Layer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;

public class MenuLayer extends Layer {

    private final Map<String, Supplier<Layer>> scenes;
    private Layer currentLayer;

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
                var instance = clazz.getDeclaredConstructor().newInstance();
//                instance.start();
                return instance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onUpdate() {
        // TODO: Remove that from here
        glClearColor(0, 0, 0, 0);
        glClear(GL_COLOR_BUFFER_BIT);
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