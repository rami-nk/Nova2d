package io.nova.core;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private final List<Component> components;
    private Vector2f position;

    public GameObject(Vector2f position) {
        components = new ArrayList<>();
        this.position = position;
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        for (var component : components) {
            if (clazz.isAssignableFrom(component.getClass())) {
                return clazz.cast(component);
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> clazz) {
        for (var component : components) {
            if (clazz.isAssignableFrom(component.getClass())) {
                components.remove(component);
                return;
            }
        }
    }

    public void addComponent(Component component) {
        components.add(component);
        component.setGameObject(this);
    }

    public void update(double deltaTime) {
        for (var component : components) {
            component.update(deltaTime);
        }
    }

    public void start() {
        for (var component : components) {
            component.start();
        }
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }
}