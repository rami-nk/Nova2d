package panels;

import imgui.ImGui;
import imgui.type.ImString;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.entity.Entity;

public class TagComponentNode {
    public static void create(Entity entity) {
        if (entity.hasComponent(TagComponent.class)) {
            var tag = entity.getComponent(TagComponent.class);
            var string = new ImString(tag.getTag(), ImString.DEFAULT_LENGTH);
            if (ImGui.inputText("Tag", string)) {
                tag.setTag(string.get());
            }
        }
    }
}