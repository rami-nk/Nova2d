package io.nova.ecs.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.nova.ecs.Scene;
import io.nova.ecs.entity.Entity;

import java.io.File;
import java.io.IOException;

public class SceneSerializer extends StdSerializer<Scene> {

    protected SceneSerializer(Class<Scene> t) {
        super(t);
    }

    public SceneSerializer() {
        this(null);
    }

    public static void serialize(Scene scene) throws IOException {
        var mapper = new ObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.NONE)
                .withGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withSetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        var file = new File("assets/scenes/Example.nova");
        new File("assets/scenes").mkdirs();
        file.createNewFile();
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, scene);
    }

    public static Scene deserialize(File file) throws IOException {
        var mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(file, Scene.class);
    }

    @Override
    public void serialize(Scene value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("viewPortWidth", value.getViewPortWidth());
        gen.writeNumberField("viewPortHeight", value.getViewPortHeight());

        gen.writeArrayFieldStart("entities");
        for (Entity entity : value.getRegistry().getEntities()) {
            gen.writeObject(entity);
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}