package io.nova.core.utils;

import io.nova.core.Texture2d;
import io.nova.core.shader.Shader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AssetProvider {

    private static final String TEXTURE_PATH_PREFIX = "src/main/resources/textures/";
    private static final String SHADER_PATH_PREFIX = "src/main/resources/shaders/";
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture2d> textures = new HashMap<>();

    public static Shader getShader(String name) {
        var fullPath = SHADER_PATH_PREFIX + name;
        if (shaders.containsKey(fullPath)) {
            return shaders.get(fullPath);
        }
        return null;
    }

    public static Shader getOrElseUploadShader(String name) {
        var shader = getShader(name);
        if (Objects.isNull(shader)) {
            uploadShader(name);
            return getShader(name);
        }
        return shader;
    }

    public static Texture2d getOrElseUploadTexture(String name) {
        var texture = getTexture(name);
        if (Objects.isNull(texture)) {
            uploadTexture(name);
            return getTexture(name);
        }
        return texture;
    }

    public static Texture2d getTexture(String name) {
        var fullPath = TEXTURE_PATH_PREFIX + name;
        if (textures.containsKey(fullPath)) {
            return textures.get(fullPath);
        }
        return null;
    }

    public static void uploadShader(String name) {
        var fullPath = SHADER_PATH_PREFIX + name;
        var shader = new Shader(fullPath);
        shaders.put(fullPath, shader);
    }

    public static void uploadTexture(String name) {
        var fullPath = TEXTURE_PATH_PREFIX + name;
        var texture = new Texture2d(fullPath);
        textures.put(fullPath, texture);
    }
}