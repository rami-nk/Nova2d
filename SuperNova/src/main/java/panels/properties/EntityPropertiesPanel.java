package panels.properties;

import io.nova.ecs.entity.Entity;

public class EntityPropertiesPanel {

    public static void create(Entity entity) {
        TagComponentNode.create(entity);
        TransformComponentNode.create(entity);
        CircleRendererComponentNode.create(entity);
        SpriteRendererComponentNode.create(entity);
        SceneCameraComponentNode.create(entity);
        BoxColliderComponentNode.create(entity);
        CircleColliderComponentNode.create(entity);
        RigidBodyComponentNode.create(entity);
    }
}