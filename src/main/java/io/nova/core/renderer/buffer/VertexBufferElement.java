package io.nova.core.renderer.buffer;

import static io.nova.core.codes.DataTypes.NV_FLOAT;
import static io.nova.core.codes.DataTypes.NV_INT;

public record VertexBufferElement(int type, int count, boolean normalized) {

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
}