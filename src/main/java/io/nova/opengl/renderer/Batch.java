package io.nova.opengl.renderer;

import io.nova.components.Sprite;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.RendererFactory;
import io.nova.core.renderer.buffer.IndexBuffer;
import io.nova.core.renderer.buffer.VertexArray;
import io.nova.core.renderer.buffer.VertexBuffer;
import io.nova.core.renderer.camera.Camera;
import io.nova.core.renderer.shader.Shader;
import io.nova.core.renderer.shader.ShaderLibrary;

import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;

public class Batch {

    private static final int POSITION_ELEMENTS_NUM = 2;
    private static final int COLOR_ELEMENTS_NUM = 4;
    private static final int TEXTURE_ELEMENTS_NUM = 2;
    private static final int TEXTURE_ID_ELEMENTS_NUM = 1;
    private static final int ELEMENTS_PER_VERTEX =
            POSITION_ELEMENTS_NUM +
                    COLOR_ELEMENTS_NUM +
                    TEXTURE_ELEMENTS_NUM +
                    TEXTURE_ID_ELEMENTS_NUM;
    private static final int VERTICES_PER_SPRITE = 4;

    private final float[] vertices;
    private final int maxBatchSize;
    private final Sprite[] sprites;
    private final TextureSlotManager textureSlotManager;
    private final Renderer renderer;
    private int numberOfSprites;
    private boolean hasRoom;
    private VertexArray vertexArray;
    private VertexBuffer vertexBuffer;
    private IndexBuffer indexBuffer;
    private Shader shader;

    public Batch(int maxBatchSize) {
        renderer = RendererFactory.create();
        hasRoom = true;
        numberOfSprites = 0;

        this.maxBatchSize = maxBatchSize;
        this.sprites = new Sprite[maxBatchSize];

        this.vertices = new float[ELEMENTS_PER_VERTEX * VERTICES_PER_SPRITE * maxBatchSize];
        this.textureSlotManager = new TextureSlotManager();
    }

    private void bindTextures() {
        int i = 0;
        for (var sprite : sprites) {
            if (sprite != null && sprite.getTextureId() != OpenGLTexture.RESERVED_TEXTURE_SLOT_ID) {
//                var texture = textureSlotManager.getTexture(sprite.getTextureId());
//                assert texture != null;
//                texture.activate(GL_TEXTURE0 + i);
//                texture.bind();
                i++;
            }
        }
    }

    private void unbindTextures() {
        for (var sprite : sprites) {
            if (sprite != null && sprite.getTextureId() != OpenGLTexture.RESERVED_TEXTURE_SLOT_ID) {
//                var texture = textureSlotManager.getTexture(sprite.getTextureId());
//                assert texture != null;
//                texture.unbind();
            }
        }
    }

    public void start() {
        vertexArray = new OpenGLVertexArray();
        vertexBuffer = new OpenGLVertexBuffer(vertices, GL_DYNAMIC_DRAW);

        var indices = generateIndices();
        indexBuffer = new OpenGLIndexBuffer(indices);

        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat("aPosition", POSITION_ELEMENTS_NUM);
        layout.pushFloat("aColor", COLOR_ELEMENTS_NUM);
        layout.pushFloat("aTextureCoords", TEXTURE_ELEMENTS_NUM);
        layout.pushFloat("aTextureId", TEXTURE_ID_ELEMENTS_NUM);
        vertexArray.addVertexBuffer(vertexBuffer, layout);
        vertexArray.setIndexBuffer(indexBuffer);

        shader = ShaderLibrary.getOrElseUpload("defaultBatch.glsl");
    }

    public void render(Camera camera) {
        // TODO: only rebuffer specific sprite

        boolean reBufferVertices = false;
        for (int index = 0; index < numberOfSprites; index++) {
            var sprite = sprites[index];
            if (sprite.isDirty()) {
                setVertexProperties(index);
                sprite.setClean();
                reBufferVertices = true;
            }
        }

        if (reBufferVertices) {
            vertexBuffer.bind();
            vertexBuffer.reBufferData(vertices);
        }

        vertexArray.bind();

        bindTextures();

        shader.bind();
        shader.setUniformMat4f("u_Projection", camera.getProjectionMatrix());

        if (textureSlotManager.hasSlots()) {
//            shader.setUniformTextureArray("u_Textures", textureSlotManager.getTextureSlots());
        }

        // TODO: refactor to use current API
//        renderer.drawQuad(vertexArray, shader);

        unbindTextures();
    }

    public void addSprite(Sprite sprite) {
        int index = numberOfSprites;
        sprites[index] = sprite;
        numberOfSprites++;

//        textureSlotManager.add(sprite.getTextureId());

        setVertexProperties(index);

        if (numberOfSprites >= maxBatchSize) {
            hasRoom = false;
        }
    }

    private void setVertexProperties(int index) {
        var sprite = sprites[index];
        int offset = index * VERTICES_PER_SPRITE * ELEMENTS_PER_VERTEX;

        var color = sprite.getColor();

        // Add vertices with the appropriate properties
        float xAdd = 1;
        float yAdd = 1;
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                yAdd = 0.0f;
            } else if (i == 2) {
                xAdd = 0.0f;
            } else if (i == 3) {
                yAdd = 1;
            }

            // set position
            vertices[offset] = sprite.getPosition().x + (xAdd * sprite.getSize().x);
            vertices[offset + 1] = sprite.getPosition().y + (yAdd * sprite.getSize().y);

            // set color
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            // set texture coords
            vertices[offset + 6] = sprite.getTextureCoordinates()[i].x;
            vertices[offset + 7] = sprite.getTextureCoordinates()[i].y;

            // set texture id
//            vertices[offset + 8] = textureSlotManager.getTextureSlot(sprite.getTextureId());

            offset += ELEMENTS_PER_VERTEX;
        }
    }

    private int[] generateIndices() {
        int[] indices = new int[ELEMENTS_PER_VERTEX * maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            var offset = VERTICES_PER_SPRITE * i;
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