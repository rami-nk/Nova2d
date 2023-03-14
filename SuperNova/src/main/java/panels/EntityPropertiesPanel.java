package panels;

import io.nova.ecs.entity.Entity;

public class EntityPropertiesPanel {

    public static void create(Entity entity) {
        TagComponentNode.create(entity);
        TransformComponentNode.create(entity);
        SpriteRenderComponentNode.create(entity);
        SceneCameraComponentNode.create(entity);
    }
}