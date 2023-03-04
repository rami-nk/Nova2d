package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLRenderer;

public interface Renderer extends QuadRenderer {

    RendererApi API = RendererApi.OpenGL;

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

    default void beginScene(OrthographicCamera camera) {}

    default void endScene() {}

    void setClearColor(float red, float green, float blue, float alpha);

    void clear();

    enum RendererApi {
        None, OpenGL
    }
}