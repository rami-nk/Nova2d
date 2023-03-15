package panels.properties;

import imgui.ImGui;
import io.nova.ecs.component.SceneCameraComponent;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.entity.Entity;

public class EntityPropertiesPanel {

    public static void create(Entity entity) {
        TagComponentNode.create(entity);
        TransformComponentNode.create(entity);
        SpriteRenderComponentNode.create(entity);
        SceneCameraComponentNode.create(entity);

        if (ImGui.button("Add component")) {
            ImGui.openPopup("AddComponent");
        }

        if (ImGui.beginPopup("AddComponent")) {
            if (ImGui.menuItem("Camera")) {
                entity.addComponent(new SceneCameraComponent());
                entity.getRegistry().updateEntity(entity);
            }
            if (ImGui.menuItem("Sprite")) {
                entity.addComponent(new SpriteRenderComponent());
                entity.getRegistry().updateEntity(entity);
            }
            ImGui.endPopup();
        }
    }
}