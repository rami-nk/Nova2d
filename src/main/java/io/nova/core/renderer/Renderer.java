package io.nova.core.renderer;

import io.nova.core.renderer.camera.OrthographicCamera;

public interface Renderer extends QuadRenderer {
    public static final RendererApi API = RendererApi.OpenGL;

    default void beginScene(OrthographicCamera camera) {}

    default void endScene() {}

    void setClearColor(float red, float green, float blue, float alpha);

    void clear();

    enum RendererApi {
        None, OpenGL
    }
}