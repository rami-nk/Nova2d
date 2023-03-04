package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLRenderer;
import org.joml.Matrix4f;
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
    default void endScene() { }
    void setClearColor(float red, float green, float blue, float alpha);
    void clear();
    void submit(VertexArray vertexArray, Shader shader, Matrix4f transform);
    default void submit(VertexArray vertexArray, Shader shader) {
        submit(vertexArray, shader, new Matrix4f());
    }
    void submit(VertexArray vertexArray, Shader shader, int count);

    default void drawQuad(Vector2f position, Vector2f size, Vector4f color) {
        drawQuad(new Vector3f(position, 0.0f), size, color);
    }
    void drawQuad(Vector3f position, Vector2f size, Vector4f color);
}