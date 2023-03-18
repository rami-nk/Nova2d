package io.nova.core.renderer;

import io.nova.ecs.component.SpriteRenderComponent;
import org.joml.Matrix4f;

public interface ComponentRenderer {

    void drawSprite(Matrix4f transform, SpriteRenderComponent component, int entityID);
}