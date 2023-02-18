package io.nova;

import static org.lwjgl.opengl.GL30.*;

public class VertexArray {

    private final int rendererId;

    public VertexArray() {
        rendererId = glGenVertexArrays();
        bind();
    }

    public void addBuffer(final VertexBuffer vertexBuffer, final VertexBufferLayout vertexBufferLayout) {
        bind();
        vertexBuffer.bind();

        var elements = vertexBufferLayout.getElements();
        int offset = 0;
        int index = 0;
        for (var element : elements) {
            glVertexAttribPointer(index, element.getCount(), element.getType(), element.getNormalized(), vertexBufferLayout.getStride(), offset);
            glEnableVertexAttribArray(index);
            offset += element.getCount() * VertexBufferElement.getSizeOfType(element.getType());
            index++;
        }

    }

    public void bind() {
        glBindVertexArray(rendererId);
    }

    public void unbind() {
        glBindVertexArray(0);
    }
}