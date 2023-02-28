package io.nova.utils;

import io.nova.renderer.OpenGLShader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShaderProvider {

    private static final String SHADER_PATH_PREFIX = "src/main/resources/shaders/";
    private static final Map<String, OpenGLShader> shaders = new HashMap<>();

    public static OpenGLShader getShader(String name) {
        var fullPath = SHADER_PATH_PREFIX + name;
        if (shaders.containsKey(fullPath)) {
            return shaders.get(fullPath);
        }
        return null;
    }

    public static OpenGLShader getOrElseUploadShader(String name) {
        var shader = getShader(name);
        if (Objects.isNull(shader)) {
            uploadShader(name);
            return getShader(name);
        }
        return shader;
    }

    public static void uploadShader(String name) {
        var fullPath = SHADER_PATH_PREFIX + name;
        var shader = new OpenGLShader(fullPath);
        shaders.put(fullPath, shader);
    }
}