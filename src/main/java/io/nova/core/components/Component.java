package io.nova.core.components;

import io.nova.core.GameObject;

public abstract class Component {

    private GameObject gameObject;

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void update(double deltaTime);

    public GameObject getGameObject() {
        return gameObject;
    }

    public void start() {

    }
}