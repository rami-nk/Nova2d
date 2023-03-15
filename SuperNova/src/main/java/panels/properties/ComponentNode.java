package panels.properties;

import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;
import io.nova.ecs.component.Component;
import io.nova.ecs.entity.Entity;

public class ComponentNode {

    public static <T extends Component> void create(Entity entity, String name, Class<T> clazz, Runnable content) {
        if (entity.hasComponent(clazz)) {
            var contentRegionAvail = ImGui.getContentRegionAvail();
            ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 4, 4);
            float lineHeight = ImGui.getFontSize() + ImGui.getStyle().getFramePadding().y * 2.0f;

            ImGui.separator();
            var open = ImGui.treeNodeEx(name, ImGuiTreeNodeFlags.DefaultOpen | ImGuiTreeNodeFlags.Framed | ImGuiTreeNodeFlags.FramePadding | ImGuiTreeNodeFlags.AllowItemOverlap);
            ImGui.popStyleVar();

            ImGui.sameLine(contentRegionAvail.x - lineHeight * 0.5f);
            if (ImGui.button("...", lineHeight, lineHeight)) {
                ImGui.openPopup("ComponentSettings");
            }

            boolean removeComponent = false;
            if (ImGui.beginPopup("ComponentSettings")) {
                if (ImGui.menuItem("Remove component")) {
                    removeComponent = true;
                }
                ImGui.endPopup();
            }

            if (open) {
                content.run();
                ImGui.treePop();
            }

            if (removeComponent) {
                // TODO: remove component
            }
        }
    }
}