package io.nova.core.renderer.buffer;

import io.nova.opengl.renderer.OpenGLVertexBufferLayout;

public interface VertexArray {
    void addBuffer(VertexBuffer vertexBuffer, OpenGLVertexBufferLayout vertexBufferLayout);

    void bind();

    void unbind();

    VertexBuffer getVertexBuffer();

    IndexBuffer getIndexBuffer();

    void setIndexBuffer(IndexBuffer indexBuffer);
}
