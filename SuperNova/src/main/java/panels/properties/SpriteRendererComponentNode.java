package panels.properties;

import imgui.ImGui;
import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.TextureLibrary;
import io.nova.ecs.component.SpriteRendererComponent;
import io.nova.ecs.entity.Entity;
import panels.DragAndDropDataType;

import java.nio.file.Path;
import java.util.Objects;

public class SpriteRendererComponentNode {
    private static final int size = 100;

    public static void create(Entity entity) {
        ComponentNode.create(entity, "Sprite", SpriteRendererComponent.class, () -> {
            var sprite = entity.getComponent(SpriteRendererComponent.class);
            var color = sprite.getColor();
            if (ImGui.colorEdit4("Color", color)) {
                sprite.setColor(color);
            }

            if (Objects.isNull(sprite.getTexture()) && Objects.isNull(sprite.getSubTexture())) {
                var grid = TextureLibrary.uploadTexture(Path.of("SuperNova/src/main/resources/icons/checkerboard.png"));
                ImGui.image(grid.getId(), size, size, 0, 1, 1, 0);
            }
            if (!Objects.isNull(sprite.getTexture())) {
                ImGui.image(sprite.getTexture().getId(), size, size, 0, 1, 1, 0);
            }
            if (!Objects.isNull(sprite.getSubTexture())) {
                var subTexture = sprite.getSubTexture();
                ImGui.image(subTexture.getTexture().getId(), size, size, subTexture.getLeftTop()[0], subTexture.getLeftTop()[1], subTexture.getRightBottom()[0], subTexture.getRightBottom()[1]);
            }

            if (ImGui.beginDragDropTarget()) {
                var payload = ImGui.acceptDragDropPayload(DragAndDropDataType.CONTENT_BROWSER_ITEM);
                var subTexturePayload = ImGui.acceptDragDropPayload(DragAndDropDataType.IMAGE_VIEWER_SUB_TEXTURE);
                if (subTexturePayload != null) {
                    var subTexture = (SubTexture) subTexturePayload;
                    sprite.setSubTexture(subTexture);
                    sprite.setTexture(null);
                }
                if (payload != null) {
                    var texturePath = ImGui.getDragDropPayload().toString();
                    sprite.setTexture(TextureLibrary.uploadTexture(Path.of(texturePath)));
                }
                ImGui.endDragDropTarget();
            }

            var tilingFactor = new float[]{sprite.getTilingFactor()};
            ImGui.dragFloat("Tiling factor", tilingFactor, 0.1f, 0.0f, 10.0f);
            sprite.setTilingFactor(tilingFactor[0]);
        });
    }
}