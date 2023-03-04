package io.nova.core.renderer.texture;

import java.util.Objects;

public class TextureLibrary {

    private static final String TEXTURE_PATH_PREFIX = "src/main/resources/textures/";
    private static final IndexedLinkedHashMap<String, Texture> textures = new IndexedLinkedHashMap<>();

    public static int upload(String name) {
        var path = TEXTURE_PATH_PREFIX + name;
        if (!textures.containsKey(path)) {
            var texture = TextureFactory.create(path);
            return textures.putIndexed(path, texture);
        }
        return textures.getIndex(path);
    }

    private static Texture uploadAndGet(String name) {
        upload(name);
        return get(name);
    }

    public static Texture getOrElseUploadTexture(String name) {
        var texture = get(name);
        if (Objects.isNull(texture)) {
            return uploadAndGet(name);
        }
        return texture;
    }

    public static Texture get(String name) {
        var path = TEXTURE_PATH_PREFIX + name;
        if (textures.containsKey(path)) {
            return textures.get(path);
        }
        return null;
    }

    public static Texture get(int textureId) {
        return textures.getValue(textureId);
    }

    public static int size() {
        return textures.size();
    }
}