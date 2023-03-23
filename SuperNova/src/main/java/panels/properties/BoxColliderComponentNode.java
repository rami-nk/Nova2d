package panels.properties;

import imgui.ImGui;
import io.nova.ecs.component.BoxColliderComponent;
import io.nova.ecs.entity.Entity;

public class BoxColliderComponentNode {
    public static void create(Entity entity) {
        ComponentNode.create(entity, "BoxCollider", BoxColliderComponent.class, () -> {
            var boxCollider = entity.getComponent(BoxColliderComponent.class);

            var offset = boxCollider.getOffset();
            if (ImGui.dragFloat2("Offset", offset, 0.1f)) {
                boxCollider.setOffset(offset);
            }

            var size = boxCollider.getSize();
            if (ImGui.dragFloat2("Size", size, 0.1f)) {
                boxCollider.setSize(size);
            }

            var friction = new float[]{boxCollider.getFriction()};
            if (ImGui.dragFloat("Friction", friction, 0.1f)) {
                boxCollider.setFriction(friction[0]);
            }

            var restitution = new float[]{boxCollider.getRestitution()};
            if (ImGui.dragFloat("Restitution", restitution, 0.1f, 0.0f, 1.0f)) {
                boxCollider.setRestitution(restitution[0]);
            }
        });
    }
}