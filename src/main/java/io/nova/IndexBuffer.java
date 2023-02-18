package io.nova;


import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;

public class IndexBuffer {

    private final int rendererId;
    private int count;

    public IndexBuffer(final int[] data) {
        rendererId = glGenBuffers();
        bind();
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(data.length);
        elementBuffer.put(data).flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
        count = elementBuffer.capacity();
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
