package io.nova.core.renderer;

import static io.nova.core.codes.DataTypes.NV_FLOAT;
import static io.nova.core.codes.DataTypes.NV_INT;

public class VertexBufferElement {
    private int type;
    private int count;
    private boolean normalized;

    public VertexBufferElement(int type, int count, boolean normalized) {
        this.type = type;
        this.count = count;
        this.normalized = normalized;
    }

    public int getType() {
        return type;
    }

    public static int getByteSize(int type) {
        switch (type) {
            case NV_FLOAT -> {
                return 4;
            }
            case NV_INT -> {
                return 1;
            }
        }
        System.err.println("Datatype not supported!");
        return -1;
    }

    public int getCount() {
        return count;
    }

    public boolean getNormalized() {
        return normalized;
    }
}