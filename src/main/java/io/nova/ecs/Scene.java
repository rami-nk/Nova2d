package io.nova.ecs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.camera.Camera;
import io.nova.core.renderer.camera.EditorCamera;
import io.nova.ecs.component.*;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.entity.EntityListener;
import io.nova.ecs.entity.Group;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.joml.Matrix4f;

import java.util.Objects;

public class Scene {

    private final Registry registry;
    @JsonIgnore
    private Renderer renderer;
    private int viewPortWidth, viewPortHeight;
    @JsonIgnore
    private World physicsWorld;

    public Scene(Renderer renderer) {
        this.renderer = renderer;
        registry = new Registry();
        registry.addEntityListener(new CameraComponentAddEventListener(), Group.create(SceneCameraComponent.class));
    }

    public Scene() {
        this(null);
    }

    private static BodyType nova2dBodyTypeToBox2DBodyType(RigidBodyComponent.BodyType type) {
        return switch (type) {
            case DYNAMIC -> BodyType.DYNAMIC;
            case STATIC -> BodyType.STATIC;
            case KINEMATIC -> BodyType.KINEMATIC;
        };
    }

    public void onRuntimeStart() {
        physicsWorld = new World(new Vec2(0, -9.81f));
        var group = registry.getEntities(Group.create(RigidBodyComponent.class));
        for (var entity : group) {
            var transform = entity.getComponent(TransformComponent.class);
            var rigidBody = entity.getComponent(RigidBodyComponent.class);

            var bodyDef = new BodyDef();
            bodyDef.setType(nova2dBodyTypeToBox2DBodyType(rigidBody.getBodyType()));
            bodyDef.setPosition(new Vec2(transform.getTranslation()[0], transform.getTranslation()[1]));
            bodyDef.setAngle(transform.getRotation()[2]);

            var body = physicsWorld.createBody(bodyDef);
            body.setFixedRotation(rigidBody.isFixedRotation());
            rigidBody.setRuntimeBody(body);

            if (entity.hasComponent(BoxColliderComponent.class)) {
                var boxCollider = entity.getComponent(BoxColliderComponent.class);
                var shape = new PolygonShape();

                shape.setAsBox(boxCollider.getSize()[0] * transform.getScale()[0], boxCollider.getSize()[1] * transform.getScale()[1]);
                var fixtureDef = new FixtureDef();
                fixtureDef.setShape(shape);
                fixtureDef.setDensity(boxCollider.getDensity());
                fixtureDef.setFriction(boxCollider.getFriction());
                fixtureDef.setRestitution(boxCollider.getRestitution());
                var fixture = body.createFixture(fixtureDef);
                boxCollider.setRuntimeFixture(fixture);
            }
        }
    }

    public void onRuntimeStop() {
        physicsWorld = null;
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
        drawPrimitives();
        renderer.endScene();
        registry.update(deltaTime);
    }

    private void drawPrimitives() {
        {
            var group = registry.getEntities(Group.create(SpriteRendererComponent.class, TransformComponent.class));
            for (var entity : group) {
                var transform = entity.getComponent(TransformComponent.class);
                var sprite = entity.getComponent(SpriteRendererComponent.class);
                renderer.drawSprite(transform.getTransform(), sprite, entity.getId());
            }
        }

        {
            var group = registry.getEntities(Group.create(CircleRendererComponent.class, TransformComponent.class));
            for (var entity : group) {
                var transform = entity.getComponent(TransformComponent.class);
                var circle = entity.getComponent(CircleRendererComponent.class);
                renderer.drawCircle(transform.getTransform(), circle, entity.getId());
            }
        }
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

        // Update physics
        {
            physicsWorld.step(deltaTime, 6, 2);
            var group = registry.getEntities(Group.create(RigidBodyComponent.class));
            for (var entity : group) {
                var transform = entity.getComponent(TransformComponent.class);
                var rigidBody = entity.getComponent(RigidBodyComponent.class);
                var body = rigidBody.getRuntimeBody();
                var position = body.getPosition();
                transform.setTranslation(new float[]{position.x, position.y, 0});
                transform.setRotation(new float[]{0, 0, body.getAngle()});
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
            drawPrimitives();
            renderer.endScene();
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

    @JsonIgnore
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

    public Scene copy() {
        var mapper = new ObjectMapper();
        return mapper.convertValue(mapper.convertValue(this, Object.class), this.getClass());
    }

    private class CameraComponentAddEventListener implements EntityListener {

        @Override
        public void entityUpdated(Entity e) {
            onViewportResize(viewPortWidth, viewPortHeight);
        }
    }
}