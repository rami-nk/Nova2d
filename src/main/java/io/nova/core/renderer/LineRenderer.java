package io.nova.core.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface LineRenderer {

    void drawLine(Vector3f p1, Vector3f p2, Vector4f color);

    default void drawRect(Vector3f pos, Vector3f size, Vector4f color) {
        var p1 = new Vector3f(pos.x - 0.5f * size.x, pos.y - 0.5f * size.y, pos.z);
        var p2 = new Vector3f(pos.x + 0.5f * size.x, pos.y - 0.5f * size.y, pos.z);
        var p3 = new Vector3f(pos.x + 0.5f * size.x, pos.y + 0.5f * size.y, pos.z);
        var p4 = new Vector3f(pos.x - 0.5f * size.x, pos.y + 0.5f * size.y, pos.z);

        drawLine(p1, p2, color);
        drawLine(p2, p3, color);
        drawLine(p3, p4, color);
        drawLine(p4, p1, color);
    }

    default void drawRect(Matrix4f transform, Vector4f color) {
        var pos = new Vector3f(transform.m30(), transform.m31(), transform.m32());
        var size = new Vector3f(transform.m00(), transform.m11(), transform.m22());
        drawRect(pos, size, color);
    }
}