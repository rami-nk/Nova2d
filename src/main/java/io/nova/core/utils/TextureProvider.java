package io.nova.core.utils;

import io.nova.core.Texture2d;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TextureProvider {

    private static final String TEXTURE_PATH_PREFIX = "src/main/resources/textures/";
    private static final IndexedLinkedHashMap<String, Texture2d> textures = new IndexedLinkedHashMap<>();

    public static int uploadTexture(String textureId) {
        var path = TEXTURE_PATH_PREFIX + textureId;
        if (!textures.containsKey(path)) {
            var texture = new Texture2d(path);
            return textures.putIndexed(path, texture);
        }
        return textures.getIndex(path);
    }

    private static Texture2d uploadAndGetTexture(String textureId) {
        uploadTexture(textureId);
        return getTexture(textureId);
    }

    public static Texture2d getOrElseUploadTexture(String textureId) {
        var texture = getTexture(textureId);
        if (Objects.isNull(texture)) {
            return uploadAndGetTexture(textureId);
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
        return textures.getValue(textureId);
    }

    public static int getNumberOfTextures() {
        return textures.size();
    }
}