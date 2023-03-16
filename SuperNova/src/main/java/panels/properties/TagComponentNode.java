package panels.properties;

import imgui.ImGui;
import imgui.type.ImString;
import io.nova.ecs.component.SceneCameraComponent;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.entity.Entity;

public class TagComponentNode {
    public static void create(Entity entity) {
        if (entity.hasComponent(TagComponent.class)) {
            var tag = entity.getComponent(TagComponent.class);
            var string = new ImString(tag.getTag(), ImString.DEFAULT_LENGTH);
            if (ImGui.inputText("##Tag", string)) {
                tag.setTag(string.get());
            }

            ImGui.sameLine();

            ImGui.pushItemWidth(-1);
            if (ImGui.button("Add component")) {
                ImGui.openPopup("AddComponent");
            }
            ImGui.popItemWidth();

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
}