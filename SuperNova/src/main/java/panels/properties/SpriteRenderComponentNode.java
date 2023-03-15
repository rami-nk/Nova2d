package panels.properties;

import imgui.ImGui;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.entity.Entity;

public class SpriteRenderComponentNode {
    public static void create(Entity entity) {
        ComponentNode.create(entity, "Sprite", SpriteRenderComponent.class, () -> {
            var sprite = entity.getComponent(SpriteRenderComponent.class);
            var color = sprite.getColor();
            if (ImGui.colorEdit4("Color", color)) {
                sprite.setColor(color);
            }
        });
    }
}