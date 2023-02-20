package io.nova.core;

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