package panels.properties;

import imgui.ImGui;
import io.nova.ecs.component.CircleRendererComponent;
import io.nova.ecs.entity.Entity;

public class CircleRendererComponentNode {
    public static void create(Entity entity) {
        ComponentNode.create(entity, "Circle", CircleRendererComponent.class, () -> {
            var circle = entity.getComponent(CircleRendererComponent.class);
            var color = circle.getColor();
            if (ImGui.colorEdit4("Color", color)) {
                circle.setColor(color);
            }
            var radius = new float[]{circle.getRadius()};
            if (ImGui.dragFloat("Radius", radius, 0.1f, 0.0f, 10.0f)) {
                circle.setRadius(radius[0]);
            }

            var thickness = new float[]{circle.getThickness()};
            if (ImGui.dragFloat("Thickness", thickness, 0.1f, 0.0f, 10.0f)) {
                circle.setThickness(thickness[0]);
            }

            var fade = new float[]{circle.getFade()};
            if (ImGui.dragFloat("Fade", fade, 0.1f, 0.0f, 10.0f)) {
                circle.setFade(fade[0]);
            }
        });
    }
}