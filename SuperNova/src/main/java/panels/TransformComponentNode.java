package panels;

import imgui.ImGui;
import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.Entity;

public class TransformComponentNode {
    public static void create(Entity entity) {
        ComponentNode.create(entity, "Transform", TransformComponent.class, () -> {
            var transform = entity.getComponent(TransformComponent.class);
            var position = transform.getTranslation();
            if (ImGui.dragFloat3("Transform", position, 0.1f)) {
                transform.setTranslation(position);
            }
        });
    }
}