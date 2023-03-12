package io.nova.ecs.system;

import io.nova.ecs.Registry;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.entity.Group;

import java.util.List;

public abstract class IteratingSystem extends EcSystem {

    private final Group group;
    private List<Entity> entities;

    public IteratingSystem(Group group) {
        if (group == null) {
            throw new NullPointerException("entity group must not be null");
        }
        this.group = group;
    }

    @Override
    public void addedToRegistry(Registry e) {
        entities = e.getEntities(group);
    }

    @Override
    public void removedFromRegistry(Registry e) {
        entities = null;
    }

    @Override
    public void update(double dt) {
        for (Entity entity : entities) {
            processEntity(entity, dt);
        }
    }

    public final List<Entity> getEntities() {
        return entities;
    }

    protected abstract void processEntity(Entity e, double dt);
}