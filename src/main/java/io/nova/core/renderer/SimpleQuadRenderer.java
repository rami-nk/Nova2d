package io.nova.core.renderer;

import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.Texture;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface SimpleQuadRenderer {
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

    void drawQuad(Matrix4f transform, Vector4f color);

    void drawQuad(Matrix4f transform, Vector4f color, int entityID);

    /*
     * ###########################################################################
     * ##########################  SUB-TEXTURES ##################################
     * ###########################################################################
     */

    void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture, float tilingFactor);

    default void drawQuad(Vector2f position, Vector2f size, SubTexture subTexture) {
        drawQuad(new Vector3f(position, 0.0f), size, subTexture);
    }

    default void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture) {
        drawQuad(new Vector3f(position.x, position.y, 0.0f), size, subTexture, 1.0f);
    }

    default void drawQuad(Vector2f position, Vector2f size, SubTexture subTexture, float tilingFactor) {
        drawQuad(new Vector3f(position.x, position.y, 0.0f), size, subTexture, tilingFactor);
    }
}