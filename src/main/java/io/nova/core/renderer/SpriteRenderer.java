package io.nova.core.renderer;

import io.nova.ecs.component.SpriteRendererComponent;
import org.joml.Matrix4f;

public interface SpriteRenderer {

    void drawSprite(Matrix4f transform, SpriteRendererComponent component, int entityID);
}