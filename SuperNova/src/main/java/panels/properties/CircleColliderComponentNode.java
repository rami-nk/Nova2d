package panels.properties;

import imgui.ImGui;
import io.nova.ecs.component.CircleColliderComponent;
import io.nova.ecs.entity.Entity;

public class CircleColliderComponentNode {
    public static void create(Entity entity) {
        ComponentNode.create(entity, "CircleCollider", CircleColliderComponent.class, () -> {
            var circleCollider = entity.getComponent(CircleColliderComponent.class);

            var offset = circleCollider.getOffset();
            if (ImGui.dragFloat2("Offset", offset, 0.1f)) {
                circleCollider.setOffset(offset);
            }

            var radius = new float[]{circleCollider.getRadius()};
            if (ImGui.dragFloat("Radius", radius, 0.01f, 0.0f, 100.0f)) {
                circleCollider.setRadius(radius[0]);
            }

            var friction = new float[]{circleCollider.getFriction()};
            if (ImGui.dragFloat("Friction", friction, 0.1f)) {
                circleCollider.setFriction(friction[0]);
            }

            var restitution = new float[]{circleCollider.getRestitution()};
            if (ImGui.dragFloat("Restitution", restitution, 0.1f, 0.0f, 1.0f)) {
                circleCollider.setRestitution(restitution[0]);
            }
        });
    }
}