package io.nova.core.scene;

import imgui.ImGui;
import io.nova.core.Nova2dWindow;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MenuScene extends Scene {

    private final Map<String, Supplier<Scene>> scenes;
    private Scene currentScene;

    public MenuScene(Scene currentScene) {
        this.currentScene = currentScene;
        this.scenes = new HashMap<>();
    }

    public <T extends Scene> void registerScene(String name, Class<T> clazz) {
        scenes.put(name, () -> {
            try {
                var instance = clazz.getDeclaredConstructor().newInstance();
                instance.start();
                return instance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        Nova2dWindow.getInstance().changeColorTo(0.0, 0.0, 0.0);
    }

    @Override
    public void imGuiRender() {
        ImGui.begin("Menu");

        for (var scene : scenes.entrySet()) {
            if (ImGui.button(scene.getKey())) {
                currentScene = scene.getValue().get();
            }
        }

        ImGui.end();
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}