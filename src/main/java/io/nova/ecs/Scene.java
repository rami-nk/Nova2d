package io.nova.ecs;

import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.camera.Camera;
import io.nova.ecs.component.*;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.entity.Group;
import io.nova.ecs.system.EcSystem;
import io.nova.ecs.system.RenderSystem;
import org.joml.Matrix4f;

public class Scene {

    private final Registry registry;
    private final Renderer renderer;

    public Scene(Renderer renderer) {
        this.renderer = renderer;
        registry = new Registry();
        registry.addSystem(new RenderSystem(renderer));
    }

    public void onUpdate(float deltaTime) {
        // Update scripts
        {
            var group = registry.getEntities(Group.create(ScriptComponent.class));
            for (var entity : group) {
                var script = entity.getComponent(ScriptComponent.class);
                if (script.getInstance() == null) {
                    script.createInstance();
                    script.setInstanceEntity();
                    script.onCreate();
                }
                script.onUpdate(deltaTime);
            }
        }

        Camera primaryCamera = null;
        Matrix4f cameraTransform = null;
        {
            var group = registry.getEntities(Group.create(SceneCameraComponent.class));
            for (var camera : group) {
                var cameraComponent = camera.getComponent(SceneCameraComponent.class);

                if (cameraComponent.isPrimary()) {
                    primaryCamera = cameraComponent.getCamera();
                    cameraTransform = camera.getComponent(TransformComponent.class).getTransform();
                    break;
                }
            }
        }

        if (primaryCamera != null) {
            renderer.beginScene(primaryCamera, cameraTransform);
            var group = registry.getEntities(Group.create(SpriteRenderComponent.class, TransformComponent.class));
            for (var entity : group) {
                var transform = entity.getComponent(TransformComponent.class);
                var sprite = entity.getComponent(SpriteRenderComponent.class);
                renderer.drawQuad(transform.getTransform(), sprite.getColor());
            }
            renderer.endScene();
        }
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

    public void addSystem(EcSystem system) {
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

    public void onViewportResize(int width, int height) {
        var group = registry.getEntities(Group.create(SceneCameraComponent.class));
        for (var camera : group) {
            var sceneCamera = camera.getComponent(SceneCameraComponent.class);

            if (!sceneCamera.isFixedAspectRatio()) {
                sceneCamera.getCamera().setViewport(width, height);
            }
        }
    }
}