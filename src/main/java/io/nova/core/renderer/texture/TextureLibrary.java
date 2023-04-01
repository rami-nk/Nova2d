package io.nova.core.renderer.texture;

import java.nio.file.Path;

public class TextureLibrary {

    private static final IndexedLinkedHashMap<String, Texture> textures = new IndexedLinkedHashMap<>();

    public static int upload(Path path) {
        var key = path.getFileName().toString();
        if (!textures.containsKey(key)) {
            var texture = TextureFactory.create(path.toAbsolutePath());
            return textures.putIndexed(key, texture);
        }
        return textures.getIndex(key);
    }

    public static Texture uploadTexture(Path path) {
        var key = path.getFileName().toString();
        if (!textures.containsKey(key)) {
            var texture = TextureFactory.create(path.toAbsolutePath());
            textures.putIndexed(key, texture);
            return texture;
        }
        return textures.get(key);
    }

    public static Texture uploadAndGet(Path path) {
        upload(path);
        var key = path.getFileName().toString();
        return get(key);
    }

    public static Texture get(String name) {
        if (textures.containsKey(name)) {
            return textures.get(name);
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