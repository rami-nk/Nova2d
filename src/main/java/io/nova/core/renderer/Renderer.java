package io.nova.core.renderer;

import io.nova.core.buffer.IndexBuffer;
import io.nova.core.buffer.VertexArray;
import io.nova.core.shader.Shader;

import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    public static void setClearColor(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public static void draw(final VertexArray vertexArray, final IndexBuffer indexBuffer, final Shader shader) {
        shader.bind();
        vertexArray.bind();
        indexBuffer.bind();
        glDrawElements(GL_TRIANGLES, indexBuffer.getCount(), GL_UNSIGNED_INT, 0);
    }

    public static void draw(final VertexArray vertexArray, final Shader shader, int count) {
        shader.bind();
        vertexArray.bind();
        glDrawArrays(GL_TRIANGLES, 0, count);
    }
}