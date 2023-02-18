package io.nova;

import io.nova.shader.Shader;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    public void setClearColor(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void draw(final VertexArray vertexArray, final Shader shader, int count) {
        shader.bind();
        vertexArray.bind();
        glDrawArrays(GL_TRIANGLES, 0, count);
    }
}