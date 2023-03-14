package panels;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import io.nova.ecs.component.Component;
import io.nova.ecs.entity.Entity;

public class ComponentNode {

    public static <T extends Component> void create(Entity entity, String name, Class<T> clazz, Runnable content) {
        if (entity.hasComponent(clazz)) {
            if (ImGui.treeNodeEx(name, ImGuiTreeNodeFlags.DefaultOpen)) {
                content.run();
                ImGui.treePop();
            }
        }
    }
}