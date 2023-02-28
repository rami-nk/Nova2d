package io.nova.utils;

import io.nova.core.renderer.Texture2d;

import java.util.Objects;

public class TextureProvider {

    private static final String TEXTURE_PATH_PREFIX = "src/main/resources/textures/";
    private static final IndexedLinkedHashMap<String, Texture2d> textures = new IndexedLinkedHashMap<>();

    public static int uploadTexture(String name) {
        var path = TEXTURE_PATH_PREFIX + name;
        if (!textures.containsKey(path)) {
            var texture = Texture2d.create(path);
            return textures.putIndexed(path, texture);
        }
        return textures.getIndex(path);
    }

    private static Texture2d uploadAndGetTexture(String name) {
        uploadTexture(name);
        return getTexture(name);
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
        return textures.getValue(textureId);
    }

    public static int getNumberOfTextures() {
        return textures.size();
    }
}