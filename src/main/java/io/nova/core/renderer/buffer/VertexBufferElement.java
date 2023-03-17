package io.nova.core.renderer.buffer;

import io.nova.core.codes.DataType;

public record VertexBufferElement(DataType type, int count, boolean normalized) {
}