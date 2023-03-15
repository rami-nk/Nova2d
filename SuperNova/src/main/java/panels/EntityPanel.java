package panels;

import imgui.ImGui;
import imgui.flag.ImGuiPopupFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import io.nova.ecs.Scene;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.entity.Entity;
import panels.properties.EntityPropertiesPanel;

import java.util.Objects;

public class EntityPanel {

    private final Scene scene;
    private Entity selectedEntity;
    private boolean removeEntityClicked = false;
    private Entity entityToRemove;

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
            createEntityPopupMenu();
        }
        ImGui.end();

        if (removeEntityClicked) {
            scene.removeEntity(entityToRemove);
            removeEntityClicked = false;
            if (Objects.equals(selectedEntity, entityToRemove)) {
                selectedEntity = null;
            }
        }

        ImGui.begin("Properties");
        {
            if (Objects.nonNull(selectedEntity)) {
                EntityPropertiesPanel.create(selectedEntity);
            }
        }
        ImGui.end();
    }

    private void createEntityPopupMenu() {
        if (ImGui.beginPopupContextWindow("NewEntity", ImGuiPopupFlags.NoOpenOverItems)) {
            if (ImGui.menuItem("New Entity")) {
                var entity = scene.createEntity();
                scene.activateEntities(entity);
                selectedEntity = entity;
            }
            ImGui.endPopup();
        }
    }

    private void createEntityNode(Entity entity) {
        String tag = entity.getComponent(TagComponent.class).getTag();
        var flags = ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.SpanAvailWidth;
        if (selectedEntity == entity) {
            flags |= ImGuiTreeNodeFlags.Selected;
        }
        boolean expanded = ImGui.treeNodeEx(tag, flags);
        if (ImGui.isItemClicked()) {
            selectedEntity = entity;
        }

        removeEntityPopupMenu(entity);

        if (expanded) {
            ImGui.treePop();
        }
    }

    private void removeEntityPopupMenu(Entity entity) {
        if (ImGui.beginPopupContextItem()) {
            if (ImGui.menuItem("Delete Entity")) {
                // Defer removal to avoid concurrent modification exception
                removeEntityClicked = true;
                entityToRemove = entity;
            }
            ImGui.endPopup();
        }
    }
}