package io.nova.opengl.renderer;

import io.nova.core.renderer.*;
import org.joml.Matrix4f;

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
    public void submit(final VertexArray vertexArray, final Shader shader, Matrix4f transform) {
        shader.bind();
        shader.setUniformMat4f("uViewProjection", camera.getViewProjectionMatrix());
        shader.setUniformMat4f("uModel", transform);

        vertexArray.bind();
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void submit(final VertexArray vertexArray, final Shader shader, int count) {
        shader.bind();
        vertexArray.bind();
        glDrawArrays(GL_TRIANGLES, 0, count);
    }
}