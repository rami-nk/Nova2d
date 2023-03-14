package panels;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.entity.Entity;

public class SpriteRenderComponentNode {
    public static void create(Entity entity) {
        if (entity.hasComponent(SpriteRenderComponent.class)) {
            if (ImGui.treeNodeEx("Sprite", ImGuiTreeNodeFlags.DefaultOpen)) {

                var sprite = entity.getComponent(SpriteRenderComponent.class);
                var color = sprite.getColorArray();
                if (ImGui.dragFloat4("Color", color, 0.01f, 0.0f, 1.0f)) {
                    sprite.setColor(color);
                }
                ImGui.treePop();
            }
        }
    }
}