package io.nova.ecs;

import io.nova.ecs.entity.Entity;

public interface EntityListener {

    void entityAdded(Entity e);

    void entityRemoved(Entity e);
}