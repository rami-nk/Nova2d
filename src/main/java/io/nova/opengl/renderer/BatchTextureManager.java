package io.nova.opengl.renderer;

import io.nova.core.renderer.Texture;
import io.nova.core.renderer.TextureLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BatchTextureManager {

    public static final int MAX_TEXTURES = 16;
    public final List<Integer> textureIds;

    private int occupiedSlots;

    public BatchTextureManager() {
        textureIds = new ArrayList<>();
        occupiedSlots = 0;
    }

    public void add(int textureId) {
        if (textureId != OpenGLTexture.RESERVED_TEXTURE_SLOT_ID) {
            if (textureIds.size() >= MAX_TEXTURES) {
                System.err.println("Maximal texture slot limit reached!");
                System.err.printf("Texture %s could be not added!", textureId);
                return;
            }
            if (!textureIds.contains(textureId)) {
                textureIds.add(textureId);
                occupiedSlots++;
            }
        }
    }

    public int[] getTextureSlots() {
        return IntStream.range(0, textureIds.size()).toArray();
    }

    public float getTextureSlot(int textureId) {
        if (textureId == OpenGLTexture.RESERVED_TEXTURE_SLOT_ID) {
            return -1;
        }
        for (int i=0; i < textureIds.size(); i++) {
            if (textureIds.get(i).equals(textureId)) {
                return i;
            }
        }
        return -1;
    }

    public Texture getTexture(int textureId) {
        return TextureLibrary.get(textureId);
    }

    public boolean hasSlots() {
        return occupiedSlots != 0;
    }
}