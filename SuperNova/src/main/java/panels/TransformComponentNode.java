package panels;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.Entity;

public class TransformComponentNode {
    public static void create(Entity entity) {
        if (entity.hasComponent(TransformComponent.class)) {
            if (ImGui.treeNodeEx("Transform", ImGuiTreeNodeFlags.DefaultOpen)) {

                var transform = entity.getComponent(TransformComponent.class);
                var position = transform.getTranslation();
                if (ImGui.dragFloat3("Transform", position, 0.1f)) {
                    transform.setTranslation(position);
                }
                ImGui.treePop();
            }
        }
    }
}