package panels;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import io.nova.ecs.Scene;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.entity.Entity;

import java.util.Objects;

public class EntityPanel {

    private final Scene scene;
    private Entity selectedEntity;

    public EntityPanel(Scene scene) {
        this.scene = scene;
    }

    public void onImGuiRender() {
        ImGui.begin("Entities");
        {
            for (var entity : scene.getRegistry().getEntities()) {
                createEntityNode(entity);
            }

            if (ImGui.isMouseDown(0) && ImGui.isWindowHovered()) {
                selectedEntity = null;
            }
        }
        ImGui.end();

        ImGui.begin("Properties");
        {
            if (Objects.nonNull(selectedEntity)) {
                EntityPropertiesPanel.create(selectedEntity);
            }
        }
        ImGui.end();
    }

    private void createEntityNode(Entity entity) {
        String tag = entity.getComponent(TagComponent.class).getTag();
        var flags = ImGuiTreeNodeFlags.OpenOnArrow;
        if (selectedEntity == entity) {
            flags |= ImGuiTreeNodeFlags.Selected;
        }
        boolean expanded = ImGui.treeNodeEx(tag, flags);
        if (ImGui.isItemClicked()) {
            selectedEntity = entity;
        }

        if (expanded) {
            ImGui.treePop();
        }
    }
}