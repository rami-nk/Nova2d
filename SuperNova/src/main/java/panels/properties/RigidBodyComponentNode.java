package panels.properties;

import imgui.ImGui;
import io.nova.ecs.component.RigidBodyComponent;
import io.nova.ecs.entity.Entity;

public class RigidBodyComponentNode {

    public static void create(Entity entity) {
        ComponentNode.create(entity, "RigidBody", RigidBodyComponent.class, () -> {
            var rigidBody = entity.getComponent(RigidBodyComponent.class);
            var currentBodyType = rigidBody.getBodyType();
            if (ImGui.beginCombo("Body type", rigidBody.getBodyType().name())) {
                for (var bodyType : RigidBodyComponent.BodyType.values()) {
                    var isSelected = bodyType == currentBodyType;
                    if (ImGui.selectable(bodyType.name(), isSelected)) {
                        currentBodyType = bodyType;
                        rigidBody.setBodyType(currentBodyType);
                    }

                    if (isSelected) {
                        ImGui.setItemDefaultFocus();
                    }
                }
                ImGui.endCombo();
            }
            ImGui.checkbox("Fixed Rotation", rigidBody.isFixedRotation());
        });
    }
}