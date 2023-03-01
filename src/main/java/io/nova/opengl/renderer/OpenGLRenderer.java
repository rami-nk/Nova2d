package io.nova.opengl.renderer;

import io.nova.core.renderer.*;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLRenderer implements Renderer {

    private OrthographicCamera camera;

    @Override
    public void beginScene(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public void setClearColor(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void draw(final VertexArray vertexArray, final Shader shader) {
        shader.bind();
        shader.setUniformMat4f("uViewProjection", camera.getViewProjectionMatrix());

        vertexArray.bind();
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void draw(final VertexArray vertexArray, final Shader shader, int count) {
        shader.bind();
        vertexArray.bind();
        glDrawArrays(GL_TRIANGLES, 0, count);
    }
}