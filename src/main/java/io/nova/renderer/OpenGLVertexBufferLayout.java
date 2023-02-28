package io.nova.renderer;

import io.nova.core.renderer.VertexBufferElement;
import io.nova.core.renderer.VertexBufferLayout;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;

public class OpenGLVertexBufferLayout implements VertexBufferLayout {

    private final List<VertexBufferElement> elements;
    private int stride;

    public OpenGLVertexBufferLayout() {
        elements = new ArrayList<>();
        stride = 0;
    }

    @Override
    public void pushFloat(int count) {
        elements.add(new VertexBufferElement(GL_FLOAT, count, false));
        stride += count * VertexBufferElement.getByteSize(GL_FLOAT);
    }

    @Override
    public void pushInt(int count) {
        elements.add(new VertexBufferElement(GL_INT, count, false));
        stride += count * VertexBufferElement.getByteSize(Integer.BYTES);
    }

    @Override
    public List<VertexBufferElement> getElements() {
        return elements;
    }

    @Override
    public int getStride() {
        return stride;
    }
}