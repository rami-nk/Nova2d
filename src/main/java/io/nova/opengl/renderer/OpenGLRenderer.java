package io.nova.opengl.renderer;

import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.camera.Camera;
import io.nova.core.renderer.camera.EditorCamera;
import io.nova.core.renderer.camera.OrthographicCamera;
import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.Texture;
import io.nova.ecs.component.CircleRendererComponent;
import io.nova.ecs.component.SpriteRendererComponent;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL40.*;

public class OpenGLRenderer implements Renderer {

    protected static final Statistics stats = new Statistics();
    protected static final int MAX_ELEMENTS = 10000;
    protected static final int MAX_INDICES = 6 * MAX_ELEMENTS;

    protected static final int VERTICES_PER_OBJECT = 4;
    private final OpenGLQuadRenderer quadRenderer;
    private final OpenGLCircleRenderer circleRenderer;
    private final OpenGLLineRenderer lineRenderer;

    public OpenGLRenderer() {
        var indices = generateIndices();

        quadRenderer = new OpenGLQuadRenderer(indices, this::endScene);
        circleRenderer = new OpenGLCircleRenderer(indices, this::endScene);
        lineRenderer = new OpenGLLineRenderer(this::endScene);
    }

    private int[] generateIndices() {
        int[] indices = new int[MAX_INDICES];
        var offset = 0;
        for (int i = 0; i < MAX_INDICES; i += 6) {
            indices[i] = offset;
            indices[i + 1] = 1 + offset;
            indices[i + 2] = 2 + offset;

            indices[i + 3] = 2 + offset;
            indices[i + 4] = 3 + offset;
            indices[i + 5] = offset;

            offset += 4;
        }
        return indices;
    }

    @Deprecated
    @Override
    public void beginScene(OrthographicCamera camera) {
        quadRenderer.beginScene(camera.getViewProjection());
        circleRenderer.beginScene(camera.getViewProjection());
        lineRenderer.beginScene(camera.getViewProjection());
    }

    @Override
    public void beginScene(Camera camera, Matrix4f transform) {
        var projection = new Matrix4f(camera.getProjection());
        transform = new Matrix4f(transform);
        var viewProjection = projection.mul(transform.invert());

        quadRenderer.beginScene(viewProjection);
        circleRenderer.beginScene(viewProjection);
        lineRenderer.beginScene(viewProjection);
    }

    @Override
    public void beginScene(EditorCamera camera) {
        quadRenderer.beginScene(camera.getViewProjection());
        circleRenderer.beginScene(camera.getViewProjection());
        lineRenderer.beginScene(camera.getViewProjection());
    }

    @Override
    public void endScene() {
        quadRenderer.endScene();
        circleRenderer.endScene();
        lineRenderer.endScene();

        flush();
    }

    @Override
    public void flush() {
        quadRenderer.flush();
        circleRenderer.flush();
        lineRenderer.flush();
    }

    @Override
    public void setClearColor(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void resetStats() {
        stats.drawCalls = 0;
        stats.quadCount = 0;
    }

    @Override
    public Statistics getStats() {
        return stats;
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Vector4f color) {
        quadRenderer.drawQuad(position, size, color);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor) {
        quadRenderer.drawQuad(position, size, texture, tilingFactor);
    }

    @Override
    public void drawQuad(Matrix4f transform, Vector4f color) {
        quadRenderer.drawQuad(transform, color);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture, float tilingFactor) {
        quadRenderer.drawQuad(position, size, subTexture, tilingFactor);
    }

    @Override
    public void drawSprite(Matrix4f transform, SpriteRendererComponent component, int entityID) {
        quadRenderer.drawSprite(transform, component, entityID);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor, Vector4f tintColor) {
        quadRenderer.drawQuad(position, size, texture, tilingFactor, tintColor);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor, Vector4f tintColor) {
        quadRenderer.drawRotatedQuad(position, size, rotation, texture, tilingFactor, tintColor);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture, float tilingFactor, Vector4f tintColor) {
        quadRenderer.drawQuad(position, size, subTexture, tilingFactor, tintColor);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, SubTexture subTexture, float tilingFactor, Vector4f tintColor) {
        quadRenderer.drawRotatedQuad(position, size, rotation, subTexture, tilingFactor, tintColor);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Vector4f color) {
        quadRenderer.drawRotatedQuad(position, size, rotation, color);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor) {
        quadRenderer.drawRotatedQuad(position, size, rotation, texture, tilingFactor);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, SubTexture subTexture, float tilingFactor) {
        quadRenderer.drawRotatedQuad(position, size, rotation, subTexture, tilingFactor);
    }

    @Override
    public void drawCircle(Matrix4f transform, CircleRendererComponent component, int entityID) {
        circleRenderer.drawCircle(transform, component, entityID);
    }

    @Override
    public void drawLine(Vector3f p1, Vector3f p2, Vector4f color) {
        lineRenderer.drawLine(p1, p2, color);
    }

    public static class Statistics {
        protected int quadCount = 0;
        protected int drawCalls = 0;

        public int getQuadCount() {
            return quadCount;
        }

        public int getDrawCalls() {
            return drawCalls;
        }

        public int getTotalVertexCount() {
            return quadCount * 4;
        }

        public int getTotalIndexCount() {
            return quadCount * 6;
        }
    }
}