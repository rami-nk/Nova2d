package panels.properties;

import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;
import io.nova.ecs.component.Component;
import io.nova.ecs.component.SceneCameraComponent;
import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.Entity;

import java.util.Objects;

public class ComponentNode {

    public static <T extends Component> void create(Entity entity, String name, Class<T> clazz, Runnable content) {
        if (entity.hasComponent(clazz)) {
            var contentRegionAvail = ImGui.getContentRegionAvail();
            ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 4, 4);
            float lineHeight = ImGui.getFontSize() + ImGui.getStyle().getFramePadding().y * 2.0f;

            ImGui.separator();
            var open = ImGui.treeNodeEx(name, ImGuiTreeNodeFlags.DefaultOpen | ImGuiTreeNodeFlags.Framed | ImGuiTreeNodeFlags.FramePadding | ImGuiTreeNodeFlags.AllowItemOverlap);
            ImGui.popStyleVar();


            var isPrimaryCamera = Objects.equals(clazz, SceneCameraComponent.class)
                    && entity.getComponent(SceneCameraComponent.class).isPrimary();
            var isTransform = Objects.equals(clazz, TransformComponent.class);
            if (isTransform || isPrimaryCamera) {
                // Do not display the remove button for the primary camera or any transform component
                // TODO: Find a better way to handle this case
                if (open) {
                    content.run();
                    ImGui.treePop();
                }
                return;
            }

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
                var component = entity.getComponent(clazz);
                entity.removeComponent(component);
            }
        }
    }
}