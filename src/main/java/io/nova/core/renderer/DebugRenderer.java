package io.nova.core.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface DebugRenderer {

    void drawLine(Vector3f p1, Vector3f p2, Vector4f color);

    /**
     * Draw a line with a given width. Max width can be limited by Graphics api.
     *
     * @param p1        First points position
     * @param p2        Second points position
     * @param lineWidth Width of the line
     * @param color     Color of the line
     */
    void drawLine(Vector3f p1, Vector3f p2, float lineWidth, Vector4f color);

    void drawRect(Vector3f pos, Vector3f size, Vector4f color);

    void drawRect(Matrix4f transform, Vector4f color);
}