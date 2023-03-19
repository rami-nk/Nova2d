package panels.properties;

import imgui.ImGui;
import io.nova.core.renderer.texture.TextureLibrary;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.entity.Entity;
import panels.DragAndDropDataType;

import java.nio.file.Path;
import java.util.Objects;

public class SpriteRenderComponentNode {
    public static void create(Entity entity) {
        ComponentNode.create(entity, "Sprite", SpriteRenderComponent.class, () -> {
            var sprite = entity.getComponent(SpriteRenderComponent.class);
            var color = sprite.getColor();
            if (ImGui.colorEdit4("Color", color)) {
                sprite.setColor(color);
            }

            if (Objects.isNull(sprite.getTexture())) {
                var grid = TextureLibrary.uploadAndGet(Path.of("SuperNova/src/main/resources/icons/checkerboard.png"));
                ImGui.image(grid.getId(), 100, 100, 0, 1, 1, 0);
            }
            if (!Objects.isNull(sprite.getTexture())) {
                ImGui.image(sprite.getTexture().getId(), 100, 100, 0, 1, 1, 0);
            }
            if (ImGui.beginDragDropTarget()) {
                var payload = ImGui.acceptDragDropPayload(DragAndDropDataType.CONTENT_BROWSER_ITEM);
                if (payload != null) {
                    var texturePath = ImGui.getDragDropPayload().toString();
                    sprite.setTexture(TextureLibrary.uploadAndGet(Path.of(texturePath)));
                }
                ImGui.endDragDropTarget();
            }

            var tilingFactor = new float[]{sprite.getTilingFactor()};
            ImGui.dragFloat("Tiling factor", tilingFactor, 0.1f, 0.0f, 10.0f);
            sprite.setTilingFactor(tilingFactor[0]);
        });
    }
}