package io.nova.renderer;

import io.nova.core.renderer.VertexArray;
import io.nova.core.renderer.VertexBuffer;
import io.nova.core.renderer.VertexBufferElement;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLVertexArray implements VertexArray {

    private final int rendererId;

    public OpenGLVertexArray() {
        rendererId = glGenVertexArrays();
        bind();
    }

    @Override
    public void addBuffer(final VertexBuffer vertexBuffer, final OpenGLVertexBufferLayout vertexBufferLayout) {
        bind();
        vertexBuffer.bind();

        var elements = vertexBufferLayout.getElements();
        int offset = 0;
        int index = 0;
        for (var element : elements) {
            glVertexAttribPointer(index, element.getCount(), element.getType(), element.getNormalized(), vertexBufferLayout.getStride(), offset);
            glEnableVertexAttribArray(index);
            offset += element.getCount() * VertexBufferElement.getByteSize(element.getType());
            index++;
        }
    }

    @Override
    public void bind() {
        glBindVertexArray(rendererId);
    }

    @Override
    public void unbind() {
        glBindVertexArray(0);
    }
}