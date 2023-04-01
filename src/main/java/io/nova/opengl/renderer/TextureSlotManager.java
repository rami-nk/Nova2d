package io.nova.opengl.renderer;

import io.nova.core.renderer.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class TextureSlotManager {

    public static final int MAX_TEXTURES = 16;
    public final List<Texture> textures;

    private int occupiedSlots;

    public TextureSlotManager() {
        textures = new ArrayList<>();
        occupiedSlots = 0;
    }

    public int add(Texture texture) {
        if (textures.size() >= MAX_TEXTURES) {
            System.err.println("Maximal texture slot limit reached!");
            System.err.printf("Texture %s could be not added!", texture);
            System.exit(-1);
        }
        if (!textures.contains(texture)) {
            textures.add(texture);
            var index = occupiedSlots;
            occupiedSlots++;
            return index;
        }
        return getTextureSlot(texture);
    }

    public int getTextureSlot(Texture texture) {
        for (int i = 1; i < textures.size(); i++) {
            if (textures.get(i).equals(texture)) {
                return i;
            }
        }
        return -1;
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public boolean hasSlots() {
        return occupiedSlots < MAX_TEXTURES;
    }

    public void reset() {
        textures.clear();
        occupiedSlots = 0;
    }
}