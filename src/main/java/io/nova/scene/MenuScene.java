package io.nova.scene;

import io.nova.KeyListener;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MenuScene extends Scene {

    private Scene currentScene;
    private final Map<String, Supplier<Scene>> scenes;

    public MenuScene(Scene currentScene) {
        this.currentScene = currentScene;
        this.scenes = new HashMap<>();
    }

    public <T extends Scene> void registerScene(String name, Class<T> clazz) {
        scenes.put(name, () -> {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void update(double deltaTime) {
        int index = 0;
        for(var scene : scenes.entrySet()) {
            if (KeyListener.isKeyPressed(index)) {
                currentScene = scene.getValue().get();
            }
        }
    }

    @Override
    public void render() {
        for (var scene : scenes.entrySet()) {
            System.out.println(scene.getKey());
        }
    }
}
