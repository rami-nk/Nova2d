package io.nova.core.utils;

import io.nova.core.Texture2d;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class TextureProvider {

    public static final int MAX_TEXTURES = 16;

    private static final String TEXTURE_PATH_PREFIX = "src/main/resources/textures/";
    private static final Map<String, Texture2d> textures = new LinkedHashMap<>();
    private static final Map<String, Integer> indices = new HashMap<>();

    private static int current = 0;

    public static int uploadTexture(String name) {
        if (textures.size() >= MAX_TEXTURES) {
            System.err.println("Maximum texture slot limit achieved!");
            return -1;
        }
        var path = TEXTURE_PATH_PREFIX + name;
        if (textures.containsKey(path)) {
            return getTextureId(path);
        }
        var texture = new Texture2d(path);
        textures.put(path, texture);
        var currentTextureId = current;
        indices.put(path, currentTextureId);
        current++;
        return currentTextureId;
    }

    private static Texture2d uploadAndGetTexture(String name) {
        var id = uploadTexture(name);
        return getTexture(id);
    }

    public static Texture2d getOrElseUploadTexture(String name) {
        var texture = getTexture(name);
        if (Objects.isNull(texture)) {
            return uploadAndGetTexture(name);
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

    private static int getTextureId(String path) {
        return indices.get(path);
    }

    public static int getNumberOfTextures() {
        return textures.size();
    }

    public static int[] getIndices() {
        return IntStream.range(0, textures.size()).toArray();
    }
}