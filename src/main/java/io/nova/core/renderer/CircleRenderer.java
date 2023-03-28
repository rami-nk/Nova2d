package io.nova.core.renderer;

import io.nova.ecs.component.CircleRendererComponent;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public interface CircleRenderer {

    default void drawCircle(Matrix4f transform, CircleRendererComponent component, int entityID) {
        drawCircle(transform, component.getColorAsVec(), component.getThickness(), component.getFade(), entityID);
    }

    void drawCircle(Matrix4f transform, Vector4f color, float thickness, float fade, int entityID);
}