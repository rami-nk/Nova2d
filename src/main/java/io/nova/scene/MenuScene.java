package io.nova.scene;

import io.nova.KeyListener;
import io.nova.Nova2dWindow;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;

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
                return clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void printInfo() {
        int index = 1;
        for (var scene : scenes.entrySet()) {
            System.out.println("Press " + index++ + " for " + scene.getKey());
        }
        System.out.println("Press BACKSPACE to go back to Menu");
    }

    @Override
    public void update(double deltaTime) {
        int index = GLFW_KEY_1;
        for (var scene : scenes.entrySet()) {
            if (KeyListener.isKeyPressed(index++)) {
                currentScene = scene.getValue().get();
            }
        }
    }

    @Override
    public void render() {
        Nova2dWindow.getInstance().changeColorTo(0.0, 0.0, 0.0);
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}