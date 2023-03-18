package io.nova.ecs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.camera.Camera;
import io.nova.core.renderer.camera.EditorCamera;
import io.nova.ecs.component.*;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.entity.EntityListener;
import io.nova.ecs.entity.Group;
import org.joml.Matrix4f;

import java.util.Objects;

public class Scene {

    private final Registry registry;
    @JsonIgnore
    private Renderer renderer;
    private int viewPortWidth, viewPortHeight;

    public Scene(Renderer renderer) {
        this.renderer = renderer;
        registry = new Registry();
        registry.addEntityListener(new CameraComponentAddEventListener(), Group.create(SceneCameraComponent.class));
    }

    public Scene() {
        this(null);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public int getViewPortWidth() {
        return viewPortWidth;
    }

    public void setViewPortWidth(int viewPortWidth) {
        this.viewPortWidth = viewPortWidth;
    }

    public int getViewPortHeight() {
        return viewPortHeight;
    }

    public void setViewPortHeight(int viewPortHeight) {
        this.viewPortHeight = viewPortHeight;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void onUpdateEditor(EditorCamera camera, float deltaTime) {
        renderer.beginScene(camera);
        var group = registry.getEntities(Group.create(SpriteRenderComponent.class, TransformComponent.class));
        for (var entity : group) {
            var transform = entity.getComponent(TransformComponent.class);
            var sprite = entity.getComponent(SpriteRenderComponent.class);
            renderer.drawQuad(transform.getTransform(), sprite.getColorAsVec(), entity.getId());
        }
        renderer.endScene();
        registry.update(deltaTime);
    }

    public void onUpdateRuntime(float deltaTime) {
        // Update scripts
        {
            var group = registry.getEntities(Group.create(ScriptComponent.class));
            for (var entity : group) {
                var script = entity.getComponent(ScriptComponent.class);
                if (Objects.isNull(script.getInstance()) || !script.isActivated()) {
                    script.createInstance();
                    script.setInstanceEntity();
                    script.onCreate();
                }

                // If the entity has a camera component we only want to update it if it is the primary camera
                var camera = entity.getComponent(SceneCameraComponent.class);
                if (Objects.isNull(camera)) {
                    script.onUpdate(deltaTime);
                } else if (camera.isPrimary()) {
                    script.onUpdate(deltaTime);
                }
            }
        }

        Camera primaryCamera = null;
        Matrix4f cameraTransform = null;
        {
            var group = registry.getEntities(Group.create(SceneCameraComponent.class));
            for (var camera : group) {
                var cameraComponent = camera.getComponent(SceneCameraComponent.class);

                if (!Objects.isNull(cameraComponent) && cameraComponent.isPrimary()) {
                    primaryCamera = cameraComponent.getCamera();
                    cameraTransform = camera.getComponent(TransformComponent.class).getTransform();
                    break;
                }
            }
        }

        if (!Objects.isNull(primaryCamera)) {
            renderer.beginScene(primaryCamera, cameraTransform);
            var group = registry.getEntities(Group.create(SpriteRenderComponent.class, TransformComponent.class));
            for (var entity : group) {
                var transform = entity.getComponent(TransformComponent.class);
                var sprite = entity.getComponent(SpriteRenderComponent.class);
                renderer.drawQuad(transform.getTransform(), sprite.getColorAsVec());
            }
            renderer.endScene();
        }

        registry.update(deltaTime);
    }

    public Entity createEntity() {
        return createEntity("");
    }

    public Entity createEntity(String name) {
        var entity = new Entity();
        entity.addComponent(new TransformComponent());
        entity.addComponent(new TagComponent(name));
        return entity;
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
        viewPortWidth = width;
        viewPortHeight = height;
        var group = registry.getEntities(Group.create(SceneCameraComponent.class));
        for (var camera : group) {
            var sceneCamera = camera.getComponent(SceneCameraComponent.class);

            if (!sceneCamera.isFixedAspectRatio()) {
                sceneCamera.getCamera().setViewport(width, height);
            }
        }
    }

    public void removeEntity(Entity entity) {
        registry.removeEntity(entity);
    }

    public Entity createVoidEntity() {
        return new Entity();
    }

    public Entity getPrimaryCameraEntity() {
        var cameraEntity = registry.getEntities(Group.create(SceneCameraComponent.class));
        for (var entity : cameraEntity) {
            var camera = entity.getComponent(SceneCameraComponent.class);
            if (camera.isPrimary()) {
                return entity;
            }
        }
        return null;
    }

    private class CameraComponentAddEventListener implements EntityListener {

        @Override
        public void entityUpdated(Entity e) {
            onViewportResize(viewPortWidth, viewPortHeight);
        }
    }
}