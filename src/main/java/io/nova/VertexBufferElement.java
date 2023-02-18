package io.nova;

import static org.lwjgl.opengl.GL11.*;

class VertexBufferElement {
    private int type;
    private int count;
    private boolean normalized;

    public VertexBufferElement(int type, int count, boolean normalized) {
        this.type = type;
        this.count = count;
        this.normalized = normalized;
    }

    public static int getSizeOfType(int type) {
        switch (type) {
            case GL_FLOAT, GL_UNSIGNED_INT -> {
                return 4;
            }
            case GL_UNSIGNED_BYTE -> {
                return 1;
            }
            default -> {
                assert false;
            }
        }
        return 0;
    }

    public int getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public boolean getNormalized() {
        return normalized;
    }
}

