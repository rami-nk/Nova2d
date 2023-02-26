package io.nova.components;

import org.joml.Vector2f;

public abstract class Component {

    private GameObject gameObject;
    Vector2f position;
    Vector2f size;
    private boolean isDirty;

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        this.position = gameObject.getPosition();
        this.size = gameObject.getSize();
        this.isDirty = true;
    }

    public void setDirty() {
        isDirty = true;
    }

    public void setClean() {
        isDirty = false;
    }

    public void update(double deltaTime) {

    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void start() {

    }

    public Vector2f getPosition() {
        return position;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }
}