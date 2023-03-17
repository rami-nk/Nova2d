package io.nova.opengl.renderer;

import io.nova.core.codes.DataType;
import io.nova.core.renderer.buffer.VertexBufferElement;
import io.nova.core.renderer.buffer.VertexBufferLayout;

import java.util.ArrayList;
import java.util.List;

public class OpenGLVertexBufferLayout implements VertexBufferLayout {

    private final List<VertexBufferElement> elements;
    private int stride;

    public OpenGLVertexBufferLayout() {
        elements = new ArrayList<>();
        stride = 0;
    }

    @Override
    public void pushFloat(String name, int count) {
        elements.add(new VertexBufferElement(DataType.FLOAT, count, false));
        stride += count * DataType.FLOAT.getByteSize();
    }

    @Override
    public void pushInt(String name, int count) {
        elements.add(new VertexBufferElement(DataType.INT, count, false));
        stride += count * DataType.INT.getByteSize();
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