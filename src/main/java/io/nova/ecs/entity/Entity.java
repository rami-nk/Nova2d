package io.nova.ecs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.nova.ecs.Registry;
import io.nova.ecs.component.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Entity {

    private static int counter = 1;
    private final List<Component> components = new ArrayList<>();
    private final Map<Class<?>, Component> cache = new HashMap<>();
    private final int id;
    private boolean activated;
    @JsonIgnore
    private Registry registry;

    public Entity() {
        id = counter++;
    }

    public int getId() {
        return id;
    }

    public List<Component> getComponents() {
        return components;
    }

    public Map<Class<?>, Component> getCache() {
        return cache;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry e) {
        registry = e;
    }

    public boolean hasComponent(Class<?> clazz) {
        if (cache.containsKey(clazz)) {
            return true;
        }

        for (Component c : components) {
            if (clazz.isInstance(c)) {
                return true;
            }
        }
        return false;
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        Component cached = cache.get(clazz);
        if (cached != null) {
            return clazz.cast(cached);
        }

        for (Component c : components) {
            if (clazz.isInstance(c)) {
                cache.put(clazz, c);
                return clazz.cast(c);
            }
        }
        return null;
    }

    public <T extends Component> List<T> getAllComponentsOfType(Class<T> clazz) {
        ArrayList<T> result = new ArrayList<>();
        for (Component c : components) {
            if (clazz.isInstance(c)) {
                result.add(clazz.cast(c));
            }
        }

        return result;
    }

    public <T extends Component> T addComponent(T c) {
        if (components.contains(c)) {
            throw new IllegalStateException(
                    "cannot add same component twice");
        }
        if (c.getEntity() != null) {
            throw new IllegalArgumentException(
                    "component already attached an entity");
        }
        components.add(c);
        c.setEntity(this);
        return c;
    }

    public <T extends Component> void removeComponent(T c) {
        if (c.getEntity() != this) {
            throw new IllegalArgumentException(
                    "component not attached to this entity");
        }
        components.remove(c);
        cache.remove(c.getClass());
        registry.removeComponent(this, c.getClass());
        c.setEntity(null);
    }

    public boolean isActivated() {
        return activated;
    }

    public void activate() {
        if (isActivated()) {
            throw new IllegalStateException("entity already activated");
        }

        // activate components
        for (Component c : components) {
            if (!c.isActivated()) {
                c.activateInternal();
            }
        }
        activated = true;
    }

    public void deactivate() {
        if (!isActivated()) {
            throw new IllegalStateException("entity not activated");
        }

        // deactivate components in reverse order
        for (int i = components.size() - 1; i >= 0; --i) {
            Component c = components.get(i);
            if (c.isActivated()) {
                c.deactivateInternal();
            }
        }
        activated = false;
    }
}