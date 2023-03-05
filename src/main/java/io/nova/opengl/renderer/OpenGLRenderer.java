package io.nova.opengl.renderer;

import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.buffer.*;
import io.nova.core.renderer.camera.OrthographicCamera;
import io.nova.core.renderer.shader.Shader;
import io.nova.core.renderer.shader.ShaderLibrary;
import io.nova.core.renderer.texture.Texture;
import io.nova.core.renderer.texture.TextureFactory;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLRenderer implements Renderer {

    private static final int MAX_QUADS = 10_000;
    private static final int ELEMENTS_PER_VERTEX = 11;
    private static final int VERTICES_PER_QUAD = 4;
    private static final int MAX_VERTICES = MAX_QUADS * VERTICES_PER_QUAD;
    private static final int MAX_INDICES = MAX_QUADS * 6;
    private final Shader shader;
    private final VertexBuffer vertexBuffer;
    private final Texture whiteTexture;
    TextureSlotManager textureSlotManager;
    private FloatBuffer quadData;
    private int indexCount;
    private Vector4f[] vertexPositions;

    public OpenGLRenderer() {
        VertexArray vertexArray = VertexArrayFactory.create();

        vertexBuffer = VertexBufferFactory.create(MAX_VERTICES);
        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat("aPosition", 3);
        layout.pushFloat("aColor", 4);
        layout.pushFloat("aTextureCoordinates", 2);
        layout.pushFloat("aTextureIndex", 1);
        layout.pushFloat("aTilingFactor", 1);
        vertexArray.addVertexBuffer(vertexBuffer, layout);

        IndexBuffer indexBuffer;
        var indices = generateIndices(MAX_INDICES);
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
        this.shader.bind();
        this.shader.setUniformMat4f("uViewProjection", camera.getViewProjectionMatrix());
        this.quadData = BufferUtils.createFloatBuffer(MAX_QUADS * ELEMENTS_PER_VERTEX * VERTICES_PER_QUAD);
        this.indexCount = 0;
    }

    @Override
    public void endScene() {
        quadData.flip();
        float[] floatArray = new float[quadData.remaining()];
        quadData.get(floatArray);
        vertexBuffer.reBufferData(floatArray);

        flush();
    }

    @Override
    public void flush() {
        var textures = textureSlotManager.getTextures();
        for (int i = 0; i < textureSlotManager.getTextures().size(); i++) {
            textures.get(i).bind(Texture.SLOT_ZERO + i);
        }

        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
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
    public void drawQuad(Vector3f position, Vector2f size, Vector4f color) {
        var whiteTextureSlot = 0.0f;

        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, 1.0f, color, whiteTextureSlot);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor) {
        var textureSlot = textureSlotManager.add(texture);
        var white = new Vector4f(1.0f);

        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, tilingFactor, white, textureSlot);
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
                .rotate((float) Math.toRadians(rotation), new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, tilingFactor, tintColor, textureSlot);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Vector4f color) {
        var textureSlot = textureSlotManager.add(whiteTexture);

        var transform = new Matrix4f()
                .translate(position)
                .rotate((float) Math.toRadians(rotation), new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, 1.0f, color, textureSlot);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor) {
        var textureSlot = textureSlotManager.add(texture);
        var white = new Vector4f(1.0f);

        var transform = new Matrix4f()
                .translate(position)
                .rotate((float) Math.toRadians(rotation), new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);

        addQuadData(transform, 1.0f, white, textureSlot);
    }

    private void addQuadData(Matrix4f transform, float tilingFactor, Vector4f color, float textureSlot) {
        for (int i = 0; i < 4; i++) {
            Vector4f transformedPos = vertexPositions[i].mul(transform, new Vector4f());

            quadData.put(transformedPos.x).put(transformedPos.y).put(transformedPos.z);
            quadData.put(color.x).put(color.y).put(color.z).put(color.w);

            switch (i) {
                case 0 -> quadData.put(0.0f).put(0.0f);
                case 1 -> quadData.put(1.0f).put(0.0f);
                case 2 -> quadData.put(1.0f).put(1.0f);
                case 3 -> quadData.put(0.0f).put(1.0f);
            }

            quadData.put(textureSlot);
            quadData.put(tilingFactor);
        }
        indexCount += 6;
    }


    private int[] generateIndices(int amount) {
        int[] indices = new int[amount];
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
}