import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImString;
import io.nova.core.renderer.camera.ProjectionType;
import io.nova.ecs.Scene;
import io.nova.ecs.component.SceneCameraComponent;
import io.nova.ecs.component.SpriteRenderComponent;
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
            if (ImGui.treeNodeEx("Transform", ImGuiTreeNodeFlags.DefaultOpen)) {

                var transform = entity.getComponent(TransformComponent.class);
                var position = transform.getTranslation();
                if (ImGui.dragFloat3("Transform", position, 0.1f)) {
                    transform.setTranslation(position);
                }
                ImGui.treePop();
            }
        }

        if (entity.hasComponent(SpriteRenderComponent.class)) {
            if (ImGui.treeNodeEx("Sprite", ImGuiTreeNodeFlags.DefaultOpen)) {

                var sprite = entity.getComponent(SpriteRenderComponent.class);
                var color = sprite.getColorArray();
                if (ImGui.dragFloat4("Color", color, 0.01f, 0.0f, 1.0f)) {
                    sprite.setColor(color);
                }
                ImGui.treePop();
            }
        }

        if (entity.hasComponent(SceneCameraComponent.class)) {
            if (ImGui.treeNodeEx("Camera", ImGuiTreeNodeFlags.DefaultOpen)) {

                var camera = entity.getComponent(SceneCameraComponent.class);
                var currentProjectionType = camera.getCamera().getProjectionType();

                if (ImGui.beginCombo("Projection", currentProjectionType.getDisplayName())) {
                    for (var projectionType : ProjectionType.values()) {
                        var isSelected = projectionType == currentProjectionType;
                        if (ImGui.selectable(projectionType.getDisplayName(), isSelected)) {
                            currentProjectionType = projectionType;
                            camera.getCamera().setProjectionType(currentProjectionType);
                        }

                        if (isSelected) {
                            ImGui.setItemDefaultFocus();
                        }
                    }
                    ImGui.endCombo();
                }

                switch (currentProjectionType) {
                    case ORTHOGRAPHIC -> {
                        var size = new float[]{camera.getCamera().getOrthographicSize()};
                        if (ImGui.dragFloat("Size", size, 0.1f)) {
                            camera.getCamera().setOrthographicSize(size[0]);
                        }
                        var nearPlane = new float[]{camera.getCamera().getOrthographicNearPlane()};
                        if (ImGui.dragFloat("Near", nearPlane, 0.1f)) {
                            camera.getCamera().setOrthographicNearPlane(nearPlane[0]);
                        }
                        var farPlane = new float[]{camera.getCamera().getOrthographicFarPlane()};
                        if (ImGui.dragFloat("Far", farPlane, 0.1f)) {
                            camera.getCamera().setOrthographicFarPlane(farPlane[0]);
                        }
                    }
                    case PERSPECTIVE -> {
                        var fov = new float[]{camera.getCamera().getFov()};
                        if (ImGui.dragFloat("FOV", fov, 0.1f)) {
                            camera.getCamera().setFov(fov[0]);
                        }
                        var nearPlane = new float[]{camera.getCamera().getPerspectiveNearPlane()};
                        if (ImGui.dragFloat("Near", nearPlane, 0.1f)) {
                            camera.getCamera().setPerspectiveNearPlane(nearPlane[0]);
                        }
                        var farPlane = new float[]{camera.getCamera().getPerspectiveFarPlane()};
                        if (ImGui.dragFloat("Far", farPlane, 0.1f)) {
                            camera.getCamera().setPerspectiveFarPlane(farPlane[0]);
                        }
                    }
                }

                ImGui.treePop();
            }
        }
    }
}