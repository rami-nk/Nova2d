package io.nova.core;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private final List<Component> components;

    public GameObject() {
        components = new ArrayList<>();
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
}