package io.nova.core.renderer;

import io.nova.ecs.component.CircleRendererComponent;
import org.joml.Matrix4f;

public interface CircleRenderer {

    void drawCircle(Matrix4f transform, CircleRendererComponent component, int entityID);
}