package io.nova.core.utils;

import io.nova.core.shader.Shader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShaderProvider {

    private static final String SHADER_PATH_PREFIX = "src/main/resources/shaders/";
    private static final Map<String, Shader> shaders = new HashMap<>();

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

    public static void uploadShader(String name) {
        var fullPath = SHADER_PATH_PREFIX + name;
        var shader = new Shader(fullPath);
        shaders.put(fullPath, shader);
    }
}