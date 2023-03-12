import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import io.nova.ecs.Scene;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.entity.Entity;

public class EntityPanel {

    private final Scene scene;
    private Entity selectedEntity;

    public EntityPanel(Scene scene) {
        this.scene = scene;
    }

    public void onImGuiRender() {
        ImGui.begin("Entities");
        for (var entity : scene.getRegistry().getEntities()) {
            drawEntityNode(entity);
        }
        ImGui.end();
    }

    private void drawEntityNode(Entity entity) {
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