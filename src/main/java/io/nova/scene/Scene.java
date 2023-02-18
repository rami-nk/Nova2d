package io.nova.scene;

public abstract class Scene {

    public Scene() {}

    public abstract void update(double deltaTime);
    public abstract void render();
}