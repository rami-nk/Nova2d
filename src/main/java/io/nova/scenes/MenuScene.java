package io.nova.scenes;

import imgui.ImGui;
import io.nova.core.Scene;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;

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
        // TODO: Remove that from here
        glClearColor(0, 0, 0, 0);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void imGuiRender() {
        for (var scene : scenes.entrySet()) {
            if (ImGui.button(scene.getKey())) {
                currentScene = scene.getValue().get();
                return;
            }
        }
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}