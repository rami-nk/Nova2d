package io.nova.renderer;

import io.nova.core.renderer.IndexBuffer;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.Shader;
import io.nova.core.renderer.VertexArray;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLRenderer implements Renderer {

    @Override
    public void setClearColor(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void draw(final VertexArray vertexArray, final IndexBuffer indexBuffer, final Shader shader) {
        shader.bind();
        vertexArray.bind();
        indexBuffer.bind();
        glDrawElements(GL_TRIANGLES, indexBuffer.getCount(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void draw(final VertexArray vertexArray, final Shader shader, int count) {
        shader.bind();
        vertexArray.bind();
        glDrawArrays(GL_TRIANGLES, 0, count);
    }
}