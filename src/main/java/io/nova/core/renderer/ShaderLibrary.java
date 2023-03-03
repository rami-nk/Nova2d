package io.nova.core.renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShaderLibrary {

    private static final String SHADER_PATH_PREFIX = "src/main/resources/shaders/";
    private static final Map<String, Shader> shaders = new HashMap<>();

    public static Shader get(String name) {
        var fullPath = SHADER_PATH_PREFIX + name;
        if (shaders.containsKey(fullPath)) {
            return shaders.get(fullPath);
        }
        return null;
    }

    public static Shader getOrElseUpload(String name) {
        var shader = get(name);
        if (Objects.isNull(shader)) {
            upload(name);
            return get(name);
        }
        return shader;
    }

    public static void upload(String name) {
        var fullPath = SHADER_PATH_PREFIX + name;
        var shader = Shader.create(fullPath);
        shaders.put(fullPath, shader);
    }
}