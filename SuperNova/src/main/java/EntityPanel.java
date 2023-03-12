import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImString;
import io.nova.ecs.Scene;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.component.TransformComponent;
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
                createPropertiesPanel(selectedEntity);
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

    private void createPropertiesPanel(Entity entity) {
        if (entity.hasComponent(TagComponent.class)) {
            var tag = entity.getComponent(TagComponent.class);
            var string = new ImString(tag.getTag(), ImString.DEFAULT_LENGTH);
            if (ImGui.inputText("Tag", string)) {
                tag.setTag(string.get());
            }
        }

        ImGui.separator();

        if (entity.hasComponent(TransformComponent.class)) {
            var transform = entity.getComponent(TransformComponent.class);
            ImGui.dragFloat3("Test", new float[3], 0.1f);
        }
    }
}