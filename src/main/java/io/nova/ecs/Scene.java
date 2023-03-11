package io.nova.ecs;

import io.nova.core.renderer.Renderer;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.system.RenderSystem;
import io.nova.ecs.system.System;

public class Scene {

    private final Registry registry;

    public Scene(Renderer renderer) {
        registry = new Registry();
        registry.addSystem(new RenderSystem(renderer));
    }

    public void onUpdate(float deltaTime) {
        registry.update(deltaTime);
    }

    public Entity createEntity() {
        var entity = new Entity();
        entity.addComponent(new TransformComponent());
        entity.addComponent(new TagComponent());
        return entity;
    }

    public Entity createEntity(String name) {
        var entity = new Entity();
        entity.addComponent(new TransformComponent());
        entity.addComponent(new TagComponent(name));
        return new Entity();
    }

    public void addSystem(System system) {
        registry.addSystem(system);
    }

    public void dispose() {
        registry.dispose();
    }

    public void activateEntities(Entity... entities) {
        for (var entity : entities) {
            registry.addEntity(entity);
        }
    }
}