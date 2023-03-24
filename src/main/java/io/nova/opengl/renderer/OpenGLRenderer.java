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

import java.util.Arrays;

import static org.lwjgl.opengl.GL40.*;

public class OpenGLRenderer implements Renderer {

    private final OpenGLQuadRenderer quadRenderer;
    private final OpenGLCircleRenderer circleRenderer;
    private final TextureSlotManager textureSlotManager;
    private final Statistics stats;

    public OpenGLRenderer() {
        textureSlotManager = new TextureSlotManager();
        quadRenderer = new OpenGLQuadRenderer(textureSlotManager, this::endScene);
        circleRenderer = new OpenGLCircleRenderer(this::endScene);
        stats = new Statistics();
    }

    @Deprecated
    @Override
    public void beginScene(OrthographicCamera camera) {
        quadRenderer.getShader().bind();
        quadRenderer.getShader().setUniformMat4f("uViewProjection", camera.getViewProjectionMatrix());

        resetData();
    }

    @Override
    public void beginScene(Camera camera, Matrix4f transform) {

        var projection = new Matrix4f(camera.getProjection());
        transform = new Matrix4f(transform);
        var viewProjection = projection.mul(transform.invert());

        quadRenderer.getShader().bind();
        quadRenderer.getShader().setUniformMat4f("uViewProjection", viewProjection);

        circleRenderer.getShader().bind();
        circleRenderer.getShader().setUniformMat4f("uViewProjection", viewProjection);

        resetData();
    }

    @Override
    public void beginScene(EditorCamera camera) {
        quadRenderer.getShader().bind();
        quadRenderer.getShader().setUniformMat4f("uViewProjection", camera.getViewProjection());

        circleRenderer.getShader().bind();
        circleRenderer.getShader().setUniformMat4f("uViewProjection", camera.getViewProjection());

        resetData();
    }

    @Override
    public void endScene() {
        if (quadRenderer.getDataIndex() > 0) {
            var copy = Arrays.copyOfRange(quadRenderer.getData(), 0, quadRenderer.getDataIndex());
            quadRenderer.getVertexBuffer().reBufferData(copy);
        }
        if (circleRenderer.getDataIndex() > 0) {
            var copy = Arrays.copyOfRange(circleRenderer.getData(), 0, circleRenderer.getDataIndex());
            circleRenderer.getVertexBuffer().reBufferData(copy);
        }

        flush();
    }

    @Override
    public void flush() {
        if (circleRenderer.getDataIndex() > 0) {
            circleRenderer.getVertexArray().bind();
            circleRenderer.getShader().bind();
            glDrawElements(GL_TRIANGLES, circleRenderer.getIndexCount(), GL_UNSIGNED_INT, 0);
            stats.drawCalls++;
        }
        if (quadRenderer.getDataIndex() > 0) {
            var textures = textureSlotManager.getTextures();
            for (int i = 0; i < textureSlotManager.getTextures().size(); i++) {
                textures.get(i).bind(Texture.SLOT_ZERO + i);
            }

            quadRenderer.getVertexArray().bind();
            quadRenderer.getShader().bind();
            glDrawElements(GL_TRIANGLES, quadRenderer.getIndexCount(), GL_UNSIGNED_INT, 0);
            stats.drawCalls++;
        }
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

    private void resetData() {
        quadRenderer.resetData();
        circleRenderer.resetData();
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

    public static class Statistics {
        private int quadCount = 0;
        private int drawCalls = 0;

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