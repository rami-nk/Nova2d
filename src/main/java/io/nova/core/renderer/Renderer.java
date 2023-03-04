package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLRenderer;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface Renderer {

    RendererApi API = RendererApi.OpenGL;

    enum RendererApi {
        None, OpenGL
    }

    static Renderer create() {
        switch (API) {
            case OpenGL -> {
                return new OpenGLRenderer();
            }
            case None -> {
                System.err.printf("%s not supported!\n", API.name());
                return null;
            }
        }
        return null;
    }

    default void beginScene(OrthographicCamera camera) { }

    default void endScene() {
    }

    void setClearColor(float red, float green, float blue, float alpha);

    void clear();

    default void drawQuad(Vector2f position, Vector2f size, Vector4f color) {
        drawQuad(new Vector3f(position, 0.0f), size, color);
    }

    void drawQuad(Vector3f position, Vector2f size, Vector4f color);

    default void drawQuad(Vector2f position, Vector2f size, Texture texture) {
        drawQuad(new Vector3f(position, 0.0f), size, texture);
    }

    default void drawQuad(Vector3f position, Vector2f size, Texture texture) {
        drawQuad(new Vector3f(position.x, position.y, 0.0f), size, texture, 1.0f);
    }

    default void drawQuad(Vector2f position, Vector2f size, Texture texture, float tilingFactor) {
        drawQuad(new Vector3f(position.x, position.y, 0.0f), size, texture, tilingFactor);
    }

    void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor);

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Vector4f color) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, color);
    }

    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Vector4f color);

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, texture);
    }

    default void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture) {
        drawRotatedQuad(new Vector3f(position.x, position.y, 0.0f), size, rotation, texture, 1.0f);
    }

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture, float tilingFactor) {
        drawRotatedQuad(new Vector3f(position.x, position.y, 0.0f), size, rotation, texture, tilingFactor);
    }

    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor);
}