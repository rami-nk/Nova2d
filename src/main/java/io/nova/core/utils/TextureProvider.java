package io.nova.core.utils;

import io.nova.core.Texture2d;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class TextureProvider {

    public static final int MAX_TEXTURES = 16;

    private static final String TEXTURE_PATH_PREFIX = "src/main/resources/textures/";
    private static final Map<String, Texture2d> textures = new HashMap<>();
    private static final Map<String, Integer> indices = new HashMap<>();

    private static int current = 0;

    public static Texture2d uploadTexture(String name) {
        var path = TEXTURE_PATH_PREFIX + name;
        var texture = new Texture2d(path);
        textures.put(path, texture);
        indices.put(path, current++);
        return texture;
    }

    public static Texture2d getOrElseUploadTexture(String name) {
        var texture = getTexture(name);
        if (Objects.isNull(texture)) {
            return uploadTexture(name);
        }
        return texture;
    }

    public static Texture2d getTexture(String name) {
        var path = TEXTURE_PATH_PREFIX + name;
        if (textures.containsKey(path)) {
            return textures.get(path);
        }
        return null;
    }

    public static Texture2d getTexture(int textureId) {
        if (textureId >= textures.size()) {
            return null;
        }

        var key = (String) textures.keySet().toArray()[textureId];
        return textures.get(key);
    }

    public static int getTextureId(String name) {
        var path = TEXTURE_PATH_PREFIX + name;
        if (textures.containsKey(path)) {
            return indices.get(path);
        }
        return -1;
    }

    public static int getNumberOfTextures() {
        return textures.size();
    }

    public static int[] getIndices() {
        return IntStream.range(0, textures.size()).toArray();
    }
}