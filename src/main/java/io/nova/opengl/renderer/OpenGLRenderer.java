package io.nova.opengl.renderer;

import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.buffer.*;
import io.nova.core.renderer.camera.Camera;
import io.nova.core.renderer.camera.OrthographicCamera;
import io.nova.core.renderer.shader.Shader;
import io.nova.core.renderer.shader.ShaderLibrary;
import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.Texture;
import io.nova.core.renderer.texture.TextureFactory;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLRenderer implements Renderer {

    private static final int MAX_QUADS = 100_000;
    private static final int ELEMENTS_PER_VERTEX = 11;
    private static final int VERTICES_PER_QUAD = 4;
    private static final int MAX_VERTICES = MAX_QUADS * VERTICES_PER_QUAD;
    private static final int MAX_INDICES = MAX_QUADS * 6;
    private final Shader shader;
    private final VertexBuffer vertexBuffer;
    private final Texture whiteTexture;
    private final Vector4f[] vertexPositions;
    private final Statistics stats;
    private final TextureSlotManager textureSlotManager;
    private float[] quadData;
    private int indexCount;
    private int quadDataIndex;

    public OpenGLRenderer() {
        VertexArray vertexArray = VertexArrayFactory.create();

        vertexBuffer = VertexBufferFactory.create(MAX_VERTICES * ELEMENTS_PER_VERTEX);
        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat("aPosition", 3);
        layout.pushFloat("aColor", 4);
        layout.pushFloat("aTextureCoordinates", 2);
        layout.pushFloat("aTextureIndex", 1);
        layout.pushFloat("aTilingFactor", 1);
        vertexArray.addVertexBuffer(vertexBuffer, layout);

        IndexBuffer indexBuffer;
        var indices = generateIndices();
        indexBuffer = IndexBufferFactory.create(indices);
        vertexArray.setIndexBuffer(indexBuffer);

        whiteTexture = TextureFactory.create(1, 1);
        whiteTexture.setData(generateWhitePixel());

        shader = ShaderLibrary.getOrElseUpload("colorAndTexture.glsl");
        shader.bind();

        int[] samplers = new int[TextureSlotManager.MAX_TEXTURES];
        for (int i = 0; i < samplers.length; i++) {
            samplers[i] = i;
        }
        shader.setUniformTextureArray("uTextures", samplers);

        this.textureSlotManager = new TextureSlotManager();
        textureSlotManager.add(whiteTexture);

        vertexPositions = new Vector4f[]{
                new Vector4f(-0.5f, -0.5f, 0.0f, 1.0f),
                new Vector4f(0.5f, -0.5f, 0.0f, 1.0f),
                new Vector4f(0.5f, 0.5f, 0.0f, 1.0f),
                new Vector4f(-0.5f, 0.5f, 0.0f, 1.0f)
        };
        stats = new Statistics();
    }

    private static ByteBuffer generateWhitePixel() {
        var whiteTextureData = BufferUtils.createByteBuffer(4);
        var oneByte = (byte) 255;
        whiteTextureData.put(new byte[]{oneByte, oneByte, oneByte, oneByte});
        whiteTextureData.flip();
        return whiteTextureData;
    }

    @Override
    public void beginScene(OrthographicCamera camera) {
        shader.bind();
        shader.setUniformMat4f("uViewProjection", camera.getViewProjectionMatrix());

        resetData();
    }

    @Override
    public void beginScene(Camera camera, Matrix4f transform) {
        shader.bind();

        var viewProjection = camera.getProjection().mul(transform.invert(), new Matrix4f());

        shader.setUniformMat4f("uViewProjection", viewProjection);

        resetData();
    }

    @Override
    public void endScene() {
        var copy = Arrays.copyOfRange(quadData, 0, quadDataIndex);
        vertexBuffer.reBufferData(copy);

        flush();
    }

    @Override
    public void flush() {
        var textures = textureSlotManager.getTextures();
        for (int i = 0; i < textureSlotManager.getTextures().size(); i++) {
            textures.get(i).bind(Texture.SLOT_ZERO + i);
        }

        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
        stats.drawCalls++;
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
        quadData = new float[MAX_QUADS * ELEMENTS_PER_VERTEX * VERTICES_PER_QUAD];
        indexCount = 0;
        quadDataIndex = 0;
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Vector4f color) {
        var whiteTextureSlot = 0.0f;

        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, 1.0f, color, whiteTextureSlot);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor) {
        var white = new Vector4f(1.0f);
        drawQuad(position, size, texture, tilingFactor, white);
    }

    @Override
    public void drawQuad(Matrix4f transform, Vector4f color) {
        var whiteTextureSlot = 0.0f;

        addQuadData(transform, 1.0f, color, whiteTextureSlot);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture, float tilingFactor) {
        var white = new Vector4f(1.0f);
        drawQuad(position, size, subTexture, tilingFactor, white);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor, Vector4f tintColor) {
        var textureSlot = textureSlotManager.add(texture);

        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, tilingFactor, tintColor, textureSlot);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor, Vector4f tintColor) {
        var textureSlot = textureSlotManager.add(texture);

        var transform = new Matrix4f()
                .translate(position)
                .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, tilingFactor, tintColor, textureSlot);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture, float tilingFactor, Vector4f tintColor) {
        var textureSlot = textureSlotManager.add(subTexture.getTexture());

        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);

        addQuadDataSubTexture(transform, tilingFactor, tintColor, textureSlot, subTexture.getTextureCoordinates());
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, SubTexture subTexture, float tilingFactor, Vector4f tintColor) {
        var textureSlot = textureSlotManager.add(whiteTexture);

        var transform = new Matrix4f()
                .translate(position)
                .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadDataSubTexture(transform, tilingFactor, tintColor, textureSlot, subTexture.getTextureCoordinates());
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Vector4f color) {
        var textureSlot = textureSlotManager.add(whiteTexture);

        var transform = new Matrix4f()
                .translate(position)
                .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, 1.0f, color, textureSlot);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor) {
        var textureSlot = textureSlotManager.add(texture);
        var white = new Vector4f(1.0f);

        var transform = new Matrix4f()
                .translate(position)
                .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, 1.0f, white, textureSlot);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, SubTexture subTexture, float tilingFactor) {
        var textureSlot = textureSlotManager.add(subTexture.getTexture());
        var white = new Vector4f(1.0f);

        var transform = new Matrix4f()
                .translate(position)
                .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadDataSubTexture(transform, tilingFactor, white, textureSlot, subTexture.getTextureCoordinates());
    }

    private void addQuadData(Matrix4f transform, float tilingFactor, Vector4f color, float textureSlot) {
        if (indexCount >= MAX_INDICES) {
            endScene();
            resetData();
        }
        for (int i = 0; i < 4; i++) {
            Vector4f transformedPos = vertexPositions[i].mul(transform, new Vector4f());

            quadData[quadDataIndex++] = transformedPos.x;
            quadData[quadDataIndex++] = transformedPos.y;
            quadData[quadDataIndex++] = transformedPos.z;

            quadData[quadDataIndex++] = color.x;
            quadData[quadDataIndex++] = color.y;
            quadData[quadDataIndex++] = color.z;
            quadData[quadDataIndex++] = color.w;

            switch (i) {
                case 0 -> {
                    quadData[quadDataIndex++] = 0.0f;
                    quadData[quadDataIndex++] = 0.0f;
                }
                case 1 -> {
                    quadData[quadDataIndex++] = 1.0f;
                    quadData[quadDataIndex++] = 0.0f;
                }
                case 2 -> {
                    quadData[quadDataIndex++] = 1.0f;
                    quadData[quadDataIndex++] = 1.0f;
                }
                case 3 -> {
                    quadData[quadDataIndex++] = 0.0f;
                    quadData[quadDataIndex++] = 1.0f;
                }
            }

            quadData[quadDataIndex++] = textureSlot;
            quadData[quadDataIndex++] = tilingFactor;
        }
        indexCount += 6;
        stats.quadCount++;
    }

    private void addQuadDataSubTexture(Matrix4f transform, float tilingFactor, Vector4f color, float textureSlot, Vector2f[] textureCoords) {
        if (indexCount >= MAX_INDICES) {
            endScene();
            resetData();
        }
        for (int i = 0; i < 4; i++) {
            Vector4f transformedPos = vertexPositions[i].mul(transform, new Vector4f());

            quadData[quadDataIndex++] = transformedPos.x;
            quadData[quadDataIndex++] = transformedPos.y;
            quadData[quadDataIndex++] = transformedPos.z;

            quadData[quadDataIndex++] = color.x;
            quadData[quadDataIndex++] = color.y;
            quadData[quadDataIndex++] = color.z;
            quadData[quadDataIndex++] = color.w;

            quadData[quadDataIndex++] = textureCoords[i].x;
            quadData[quadDataIndex++] = textureCoords[i].y;

            quadData[quadDataIndex++] = textureSlot;
            quadData[quadDataIndex++] = tilingFactor;
        }
        indexCount += 6;
        stats.quadCount++;
    }

    private int[] generateIndices() {
        int[] indices = new int[OpenGLRenderer.MAX_INDICES];
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