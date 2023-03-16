package io.nova.ecs.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.nova.ecs.Scene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SceneSerializer {

    private final ObjectMapper mapper;

    public SceneSerializer() {
        this.mapper = new ObjectMapper();

        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.NONE)
                .withGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withSetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        // register custom deserializer
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Scene.class, new SceneDeserializer());
        mapper.registerModule(module);
    }

    public Scene deserialize(String path) throws IOException {
        var content = Files.readString(Path.of(path));
        return mapper.readValue(content, Scene.class);
    }

    public void serialize(Scene context, String path) throws IOException {
        var file = new File(path);
        var ensureParentDirectoriesExist = file.getParentFile().mkdirs();
        var fileCreated = file.createNewFile();
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, context);
    }
}