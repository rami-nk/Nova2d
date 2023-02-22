package io.nova.core.renderer;

import io.nova.core.Camera;
import io.nova.core.buffer.IndexBuffer;
import io.nova.core.buffer.VertexArray;
import io.nova.core.buffer.VertexBuffer;
import io.nova.core.buffer.VertexBufferLayout;
import io.nova.core.components.Sprite;
import io.nova.core.shader.Shader;

import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
public class Batch {

    private static final int POSITION_ELEMENTS_NUM = 2;
    private static final int COLOR_ELEMENTS_NUM = 4;
    private static final int ELEMENTS_PER_VERTEX = POSITION_ELEMENTS_NUM + COLOR_ELEMENTS_NUM;
    private static final int ELEMENTS_PER_SPRITE = 4;

    private float[] vertices;
    private int maxBatchSize;
    private Sprite[] sprites;
    private int numberOfSprites;
    private boolean hasRoom;
    private VertexArray vertexArray;
    private VertexBuffer vertexBuffer;
    private IndexBuffer indexBuffer;
    private Shader shader;

    public Batch(int maxBatchSize) {
        hasRoom = true;
        numberOfSprites = 0;

        this.maxBatchSize = maxBatchSize;
        this.sprites = new Sprite[maxBatchSize];

        this.vertices = new float[ELEMENTS_PER_VERTEX * ELEMENTS_PER_SPRITE * maxBatchSize];
    }

    public void start() {
        vertexArray = new VertexArray();
        vertexBuffer = new VertexBuffer(vertices, GL_DYNAMIC_DRAW);

        var indices = generateIndices();
        indexBuffer = new IndexBuffer(indices);

        var layout = new VertexBufferLayout();
        layout.pushFloat(POSITION_ELEMENTS_NUM);
        layout.pushFloat(COLOR_ELEMENTS_NUM);
        vertexArray.addBuffer(vertexBuffer, layout);

        shader = new Shader("src/main/resources/shaders/default.glsl");
    }

    public void render(Camera camera) {
        // TODO: only rebuffer specific spride
        vertexBuffer.bind();
        vertexBuffer.reBufferData(vertices);

        shader.bind();

        vertexArray.bind();

        Renderer.draw(vertexArray, indexBuffer, shader);
    }

    public void addSprite(Sprite sprite) {
        int index = numberOfSprites;
        sprites[index] = sprite;
        numberOfSprites++;

        // add properties to local properties array
        loadVertexProperties(index);

        if (numberOfSprites >= maxBatchSize) {
            hasRoom = false;
        }
    }

    private void loadVertexProperties(int index) {
        var sprite = sprites[index];
        int offset = index * ELEMENTS_PER_SPRITE * ELEMENTS_PER_VERTEX;

        var color = sprite.getColor();

        // Add vertices with the appropriate properties
        float xAdd = 3;
        float yAdd = 3;
        for (int i=0; i < 4; i++) {
            if (i == 1) {
                yAdd = 0.0f;
            } else if (i == 2) {
                xAdd = 0.0f;
            } else if (i == 3) {
                yAdd = 3;
            }

            // Load position
            vertices[offset] = sprite.getGameObject().getPosition().x + xAdd;
            vertices[offset + 1] = sprite.getGameObject().getPosition().y + yAdd;

            // Load color
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            offset += ELEMENTS_PER_VERTEX;
        }
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

    public boolean hasRoom() {
        return hasRoom;
    }
}