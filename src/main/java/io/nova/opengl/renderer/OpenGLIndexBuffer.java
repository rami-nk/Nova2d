package io.nova.opengl.renderer;

import io.nova.core.renderer.IndexBuffer;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLIndexBuffer implements IndexBuffer {

    private final int rendererId;
    private final int count;

    public OpenGLIndexBuffer(final int[] data) {
        rendererId = glGenBuffers();
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        count = data.length;
    }

    @Override
    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rendererId);
    }

    @Override
    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public int getCount() {
        return count;
    }
}