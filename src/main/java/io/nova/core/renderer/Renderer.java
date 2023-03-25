package io.nova.core.renderer;

import io.nova.core.renderer.camera.Camera;
import io.nova.core.renderer.camera.EditorCamera;
import io.nova.core.renderer.camera.OrthographicCamera;
import io.nova.opengl.renderer.OpenGLRenderer;
import org.joml.Matrix4f;

public interface Renderer extends
        QuadRenderer,
        SpriteRenderer,
        CircleRenderer,
        LineRenderer {
    RendererApi API = RendererApi.OpenGL;

    void beginScene(OrthographicCamera camera);

    void beginScene(Camera camera, Matrix4f transform);

    void beginScene(EditorCamera camera);

    void endScene();

    void flush();

    void setClearColor(float red, float green, float blue, float alpha);

    void clear();

    void resetStats();

    OpenGLRenderer.Statistics getStats();

    enum RendererApi {
        None, OpenGL
    }
}