package io.nova.core;

import io.nova.components.GameObject;
import io.nova.renderer.BatchRenderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    private final List<GameObject> gameObjects;
    private final BatchRenderer batchRenderer;

    private boolean isRunning;

    public Scene() {
        isRunning = false;
        gameObjects = new ArrayList<>();
        batchRenderer = new BatchRenderer();
    }

    public void start() {
        for (GameObject gameObject : gameObjects) {
            gameObject.start();
            batchRenderer.add(gameObject);
        }
        isRunning = true;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void addGameObjectToScene(GameObject gameObject) {
        gameObjects.add(gameObject);
        if (isRunning) {
            gameObject.start();
            batchRenderer.add(gameObject);
        }
    }

    public abstract void update(double deltaTime);

    public abstract void render();

    public void imGuiRender() {

    }

    public BatchRenderer getBatchRenderer() {
        return batchRenderer;
    }
}