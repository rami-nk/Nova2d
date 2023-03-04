package io.nova.opengl.renderer;

import io.nova.core.renderer.buffer.IndexBuffer;
import io.nova.core.renderer.buffer.VertexArray;
import io.nova.core.renderer.buffer.VertexBuffer;
import io.nova.core.renderer.buffer.VertexBufferElement;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLVertexArray implements VertexArray {

    private final int rendererId;
    private IndexBuffer indexBuffer;
    private VertexBuffer vertexBuffer;

    public OpenGLVertexArray() {
        rendererId = glGenVertexArrays();
        bind();
    }

    @Override
    public void addBuffer(final VertexBuffer vertexBuffer, final OpenGLVertexBufferLayout vertexBufferLayout) {
        this.vertexBuffer = vertexBuffer;
        bind();
        vertexBuffer.bind();

        var elements = vertexBufferLayout.getElements();
        int offset = 0;
        int index = 0;
        for (var element : elements) {
            glVertexAttribPointer(index, element.count(), element.type(), element.normalized(), vertexBufferLayout.getStride(), offset);
            glEnableVertexAttribArray(index);
            offset += element.count() * VertexBufferElement.getByteSize(element.type());
            index++;
        }
    }

    @Override
    public void setIndexBuffer(IndexBuffer indexBuffer) {
        this.indexBuffer = indexBuffer;
    }

    @Override
    public void bind() {
        glBindVertexArray(rendererId);
    }

    @Override
    public void unbind() {
        glBindVertexArray(0);
    }

    @Override
    public VertexBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    @Override
    public IndexBuffer getIndexBuffer() {
        return indexBuffer;
    }
}