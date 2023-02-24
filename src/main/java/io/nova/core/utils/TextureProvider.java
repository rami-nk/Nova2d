package io.nova.core.utils;

import io.nova.core.Texture2d;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TextureProvider {

    private static final String TEXTURE_PATH_PREFIX = "src/main/resources/textures/";
    private static final Map<String, Texture2d> textures = new HashMap<>();

    public static void uploadTexture(String textureId) {
        var path = TEXTURE_PATH_PREFIX + textureId;
        if (!textures.containsKey(path)) {
            var texture = new Texture2d(path);
            textures.put(path, texture);
        }
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

    public static Texture2d getTexture(String textureId) {
        var path = TEXTURE_PATH_PREFIX + textureId;
        if (textures.containsKey(path)) {
            return textures.get(path);
        }
        return null;
    }

    public static int getNumberOfTextures() {
        return textures.size();
    }
}