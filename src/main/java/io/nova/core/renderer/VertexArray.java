package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLVertexBufferLayout;

public interface VertexArray {
    void addBuffer(VertexBuffer vertexBuffer, OpenGLVertexBufferLayout vertexBufferLayout);
    void setIndexBuffer(IndexBuffer indexBuffer);
    void bind();
    void unbind();
    VertexBuffer getVertexBuffer();
    IndexBuffer getIndexBuffer();
}
