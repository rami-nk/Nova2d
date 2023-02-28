package io.nova.core.renderer;

import io.nova.renderer.OpenGLVertexBufferLayout;

public interface VertexArray {
    void addBuffer(VertexBuffer vertexBuffer, OpenGLVertexBufferLayout vertexBufferLayout);

    void bind();

    void unbind();
}
