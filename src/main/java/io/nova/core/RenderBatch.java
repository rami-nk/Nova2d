package io.nova.core;

import io.nova.core.buffer.IndexBuffer;
import io.nova.core.buffer.VertexArray;
import io.nova.core.buffer.VertexBuffer;
import io.nova.core.buffer.VertexBufferLayout;
import io.nova.core.components.SpriteRenderer;
import io.nova.core.shader.Shader;

import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;

public class RenderBatch {

    private static final int POSITION_ELEMENTS_NUM = 2;
    private static final int COLOR_ELEMENTS_NUM = 4;
    private static final int ELEMENTS_PER_VERTEX = POSITION_ELEMENTS_NUM + COLOR_ELEMENTS_NUM;
    private static final int ELEMENTS_PER_SPRITE = 4;

    private float[] vertices;
    private int maxBatchSize;
    private SpriteRenderer[] sprites;
    private int numberOfSprites;
    private boolean hasRoom;
    private VertexArray vertexArray;
    private VertexBuffer vertexBuffer;
    private IndexBuffer indexBuffer;
    private Shader shader;

    public RenderBatch(int maxBatchSize) {
        hasRoom = false;
        numberOfSprites = 0;

        this.maxBatchSize = maxBatchSize;
        this.sprites = new SpriteRenderer[maxBatchSize];

        this.vertices = new float[ELEMENTS_PER_VERTEX * ELEMENTS_PER_SPRITE * maxBatchSize];
    }

    public void start() {
        vertexArray = new VertexArray();
        VertexBuffer vertexBuffer = new VertexBuffer(vertices, GL_DYNAMIC_DRAW);

        var indices = generateIndices();
        indexBuffer = new IndexBuffer(indices);

        var layout = new VertexBufferLayout();
        layout.pushFloat(POSITION_ELEMENTS_NUM);
        layout.pushFloat(COLOR_ELEMENTS_NUM);
        vertexArray.addBuffer(vertexBuffer, layout);

        shader = new Shader("src/main/resources/default.glsl");
    }

    public void render() {
        // TODO: only rebuffer specific spride
        vertexBuffer.bind();
        vertexBuffer.reBufferData(vertices);

        shader.bind();

        vertexArray.bind();

        Renderer.draw(vertexArray, indexBuffer, shader);
    }

    public void addSprite(SpriteRenderer spriteRenderer) {
        int index = numberOfSprites;
        sprites[index] = spriteRenderer;
        numberOfSprites++;

        loadVertexProperties(index);

        if (numberOfSprites > maxBatchSize) {
            hasRoom = false;
        }
    }

    private void loadVertexProperties(int index) {
        var spriteRenderer = sprites[index];
        int offset = index * ELEMENTS_PER_SPRITE * ELEMENTS_PER_VERTEX;
    }

    private int[] generateIndices() {
        int[] indices = new int[ELEMENTS_PER_VERTEX * maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            var offset = ELEMENTS_PER_SPRITE * i;
            var index = i * ELEMENTS_PER_VERTEX;
            indices[index] = 3 + offset;
            indices[index + 1] = 2 + offset;
            indices[index + 2] = offset;
            indices[index + 3] = offset;
            indices[index + 4] = 2 + offset;
            indices[index + 5] = 1 + offset;
        }
        return indices;
    }
}