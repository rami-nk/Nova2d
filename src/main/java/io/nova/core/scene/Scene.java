package io.nova.core.scene;

import io.nova.core.GameObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    private final List<GameObject> gameObjects;
    private boolean isRunning;

    public Scene() {
        isRunning = false;
        gameObjects = new ArrayList<>();
    }

    public void start() {
        for (GameObject gameObject : gameObjects) {
            gameObject.start();
        }
    }

    public  void addGameObjectToScene(GameObject gameObject) {
        gameObjects.add(gameObject);
        if (isRunning) {
            gameObject.start();
        }
    }

    public abstract void update(double deltaTime);

    public abstract void render();
}