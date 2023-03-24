package io.nova.opengl.renderer;

import io.nova.core.renderer.buffer.*;
import io.nova.core.renderer.shader.Shader;
import io.nova.core.renderer.shader.ShaderLibrary;
import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.Texture;
import io.nova.core.renderer.texture.TextureFactory;
import io.nova.ecs.component.SpriteRendererComponent;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;

public class OpenGLQuadRenderer {

    private static final int MAX_QUADS = 1;
    private static final int ELEMENTS_PER_VERTEX = 12;
    private static final int VERTICES_PER_QUAD = 4;
    private static final int MAX_VERTICES = MAX_QUADS * VERTICES_PER_QUAD;
    private static final int MAX_INDICES = MAX_QUADS * 6;
    private final Shader shader;
    private final VertexBuffer vertexBuffer;
    private final VertexArray vertexArray;
    private final Texture whiteTexture;
    private final Vector4f[] vertexPositions;
    private final TextureSlotManager textureSlotManager;
    private final Runnable endSceneCallback;
    private float[] data;
    private int indexCount;
    private int dataIndex;

    public OpenGLQuadRenderer(TextureSlotManager textureSlotManager, Runnable endSceneCallback) {
        this.textureSlotManager = textureSlotManager;
        this.endSceneCallback = endSceneCallback;

        vertexArray = VertexArrayFactory.create();
        vertexBuffer = VertexBufferFactory.create(MAX_VERTICES * ELEMENTS_PER_VERTEX);
        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat("aPosition", 3);
        layout.pushFloat("aColor", 4);
        layout.pushFloat("aTextureCoordinates", 2);
        layout.pushFloat("aTextureIndex", 1);
        layout.pushFloat("aTilingFactor", 1);
        // Editor only
        layout.pushFloat("aEntityID", 1);
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

        textureSlotManager.add(whiteTexture);

        vertexPositions = new Vector4f[]{
                new Vector4f(-0.5f, -0.5f, 0.0f, 1.0f),
                new Vector4f(0.5f, -0.5f, 0.0f, 1.0f),
                new Vector4f(0.5f, 0.5f, 0.0f, 1.0f),
                new Vector4f(-0.5f, 0.5f, 0.0f, 1.0f)
        };
    }

    private static ByteBuffer generateWhitePixel() {
        var whiteTextureData = BufferUtils.createByteBuffer(4);
        var oneByte = (byte) 255;
        whiteTextureData.put(new byte[]{oneByte, oneByte, oneByte, oneByte});
        whiteTextureData.flip();
        return whiteTextureData;
    }

    void resetData() {
        data = new float[MAX_QUADS * ELEMENTS_PER_VERTEX * VERTICES_PER_QUAD];
        indexCount = 0;
        dataIndex = 0;
    }

    public void drawQuad(Vector3f position, Vector2f size, Vector4f color) {
        var whiteTextureSlot = 0.0f;

        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, 1.0f, color, whiteTextureSlot);
    }

    public void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor) {
        var white = new Vector4f(1.0f);
        drawQuad(position, size, texture, tilingFactor, white);
    }

    public void drawQuad(Matrix4f transform, Vector4f color) {
        var whiteTextureSlot = 0.0f;

        addQuadData(transform, 1.0f, color, whiteTextureSlot);
    }

    public void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture, float tilingFactor) {
        var white = new Vector4f(1.0f);
        drawQuad(position, size, subTexture, tilingFactor, white);
    }

    public void drawSprite(Matrix4f transform, SpriteRendererComponent component, int entityID) {

        if (component.getTexture() != null) {
            var textureSlot = textureSlotManager.add(component.getTexture());
            addQuadData(transform, component.getTilingFactor(), component.getColorAsVec(), textureSlot, entityID);
        } else {
            var whiteTextureSlot = 0.0f;
            addQuadData(transform, 1.0f, component.getColorAsVec(), whiteTextureSlot, entityID);
        }
    }

    public void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor, Vector4f tintColor) {
        var textureSlot = textureSlotManager.add(texture);

        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, tilingFactor, tintColor, textureSlot);
    }

    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor, Vector4f tintColor) {
        var textureSlot = textureSlotManager.add(texture);

        var transform = new Matrix4f()
                .translate(position)
                .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, tilingFactor, tintColor, textureSlot);
    }

    public void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture, float tilingFactor, Vector4f tintColor) {
        var textureSlot = textureSlotManager.add(subTexture.getTexture());

        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);

        addQuadDataSubTexture(transform, tilingFactor, tintColor, textureSlot, subTexture.getTextureCoordinates());
    }

    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, SubTexture subTexture, float tilingFactor, Vector4f tintColor) {
        var textureSlot = textureSlotManager.add(whiteTexture);

        var transform = new Matrix4f()
                .translate(position)
                .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadDataSubTexture(transform, tilingFactor, tintColor, textureSlot, subTexture.getTextureCoordinates());
    }

    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Vector4f color) {
        var textureSlot = textureSlotManager.add(whiteTexture);

        var transform = new Matrix4f()
                .translate(position)
                .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, 1.0f, color, textureSlot);
    }

    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor) {
        var textureSlot = textureSlotManager.add(texture);
        var white = new Vector4f(1.0f);

        var transform = new Matrix4f()
                .translate(position)
                .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, 1.0f, white, textureSlot);
    }

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
        addQuadData(transform, tilingFactor, color, textureSlot, -1);
    }

    private void addQuadData(Matrix4f transform, float tilingFactor, Vector4f color, float textureSlot, int entityID) {
        if (indexCount >= MAX_INDICES) {
            endSceneCallback.run();
            resetData();
        }
        for (int i = 0; i < 4; i++) {
            Vector4f transformedPos = vertexPositions[i].mul(transform, new Vector4f());

            data[dataIndex++] = transformedPos.x;
            data[dataIndex++] = transformedPos.y;
            data[dataIndex++] = transformedPos.z;

            data[dataIndex++] = color.x;
            data[dataIndex++] = color.y;
            data[dataIndex++] = color.z;
            data[dataIndex++] = color.w;

            switch (i) {
                case 0 -> {
                    data[dataIndex++] = 0.0f;
                    data[dataIndex++] = 0.0f;
                }
                case 1 -> {
                    data[dataIndex++] = 1.0f;
                    data[dataIndex++] = 0.0f;
                }
                case 2 -> {
                    data[dataIndex++] = 1.0f;
                    data[dataIndex++] = 1.0f;
                }
                case 3 -> {
                    data[dataIndex++] = 0.0f;
                    data[dataIndex++] = 1.0f;
                }
            }

            data[dataIndex++] = textureSlot;
            data[dataIndex++] = tilingFactor;
            data[dataIndex++] = entityID;
        }
        indexCount += 6;
    }

    private void addQuadDataSubTexture(Matrix4f transform, float tilingFactor, Vector4f color, float textureSlot, Vector2f[] textureCoords) {
        if (indexCount >= MAX_INDICES) {
            endSceneCallback.run();
            resetData();
        }
        for (int i = 0; i < 4; i++) {
            Vector4f transformedPos = vertexPositions[i].mul(transform, new Vector4f());

            data[dataIndex++] = transformedPos.x;
            data[dataIndex++] = transformedPos.y;
            data[dataIndex++] = transformedPos.z;

            data[dataIndex++] = color.x;
            data[dataIndex++] = color.y;
            data[dataIndex++] = color.z;
            data[dataIndex++] = color.w;

            data[dataIndex++] = textureCoords[i].x;
            data[dataIndex++] = textureCoords[i].y;

            data[dataIndex++] = textureSlot;
            data[dataIndex++] = tilingFactor;
        }
        indexCount += 6;
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

    public void beginScene(Matrix4f viewProjection) {
        shader.bind();
        shader.setUniformMat4f("uViewProjection", viewProjection);
        resetData();
    }

    public void endScene() {
        if (dataIndex > 0) {
            var copy = Arrays.copyOfRange(data, 0, dataIndex);
            vertexBuffer.reBufferData(copy);
        }
    }

    public void flush() {
        if (dataIndex > 0) {
            var textures = textureSlotManager.getTextures();
            for (int i = 0; i < textureSlotManager.getTextures().size(); i++) {
                textures.get(i).bind(Texture.SLOT_ZERO + i);
            }

            vertexArray.bind();
            shader.bind();
            glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
//            stats.drawCalls++;
        }
    }
}
