package io.nova.core.renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface DefaultQuadRenderer {

    void drawQuad(Vector3f position, Vector2f size, Vector4f color);

    void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor);

    default void drawQuad(Vector2f position, Vector2f size, Vector4f color) {
        drawQuad(new Vector3f(position, 0.0f), size, color);
    }

    default void drawQuad(Vector2f position, Vector2f size, Texture texture) {
        drawQuad(new Vector3f(position, 0.0f), size, texture);
    }

    default void drawQuad(Vector3f position, Vector2f size, Texture texture) {
        drawQuad(new Vector3f(position.x, position.y, 0.0f), size, texture, 1.0f);
    }

    default void drawQuad(Vector2f position, Vector2f size, Texture texture, float tilingFactor) {
        drawQuad(new Vector3f(position.x, position.y, 0.0f), size, texture, tilingFactor);
    }
}