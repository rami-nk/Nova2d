package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLRenderer;

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

    void setClearColor(float red, float green, float blue, float alpha);

    void clear();

    void draw(VertexArray vertexArray, IndexBuffer indexBuffer, Shader shader);

    void draw(VertexArray vertexArray, Shader shader, int count);
}