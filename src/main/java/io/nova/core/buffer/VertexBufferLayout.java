package io.nova.core.buffer;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

public class VertexBufferLayout {

    private final List<VertexBufferElement> elements;
    private int stride;

    public VertexBufferLayout() {
        elements = new ArrayList<>();
        stride = 0;
    }

    public void pushFloat(int count) {
        elements.add(new VertexBufferElement(GL_FLOAT, count, false));
        stride += count * VertexBufferElement.getSizeOfType(GL_FLOAT);
    }

    public List<VertexBufferElement> getElements() {
        return elements;
    }

    public int getStride() {
        return stride;
    }
}