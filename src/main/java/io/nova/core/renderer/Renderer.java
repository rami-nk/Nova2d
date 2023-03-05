package io.nova.core.renderer;

import io.nova.core.renderer.camera.OrthographicCamera;
import io.nova.opengl.renderer.OpenGLRenderer;

public interface Renderer extends QuadRenderer {
    RendererApi API = RendererApi.OpenGL;

    void beginScene(OrthographicCamera camera);

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