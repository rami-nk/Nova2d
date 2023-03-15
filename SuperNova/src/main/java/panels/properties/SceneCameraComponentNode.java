package panels.properties;

import imgui.ImGui;
import io.nova.core.renderer.camera.ProjectionType;
import io.nova.ecs.component.SceneCameraComponent;
import io.nova.ecs.entity.Entity;

public class SceneCameraComponentNode {
    public static void create(Entity entity) {
        ComponentNode.create(entity, "Camera", SceneCameraComponent.class, () -> {
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
                    var fov = new float[]{(float) Math.toDegrees(camera.getCamera().getFov())};
                    if (ImGui.dragFloat("FOV", fov, 0.1f)) {
                        camera.getCamera().setFov((float) Math.toRadians(fov[0]));
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

            var isPrimary = camera.isPrimary();
            if (ImGui.checkbox("Primary", isPrimary)) {
                camera.setPrimary(!isPrimary);
            }
        });
    }
}