package io.nova.core.buffer;


import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;

public class IndexBuffer {

    private final int rendererId;
    private final int count;

    public IndexBuffer(final int[] data) {
        rendererId = glGenBuffers();
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        count = data.length;
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rendererId);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getCount() {
        return count;
    }
}