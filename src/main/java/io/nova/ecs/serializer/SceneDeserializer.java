package io.nova.ecs.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.nova.ecs.Scene;
import io.nova.ecs.component.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class SceneDeserializer extends StdDeserializer<Scene> {

    public SceneDeserializer() {
        this(null);
    }

    protected SceneDeserializer(Class<?> vc) {
        super(vc);
    }

    public static Scene deserialize(String path) throws IOException {
        var content = Files.readString(Path.of(path));
        var mapper = new ObjectMapper(new YAMLFactory());

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Scene.class, new SceneDeserializer(Scene.class));
        mapper.registerModule(module);

        return mapper.readValue(content, Scene.class);
    }

    @Override
    public Scene deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        var mapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);

        Scene scene = new Scene();
        scene.setViewPortWidth(node.get("viewPortWidth").asInt());
        scene.setViewPortHeight(node.get("viewPortHeight").asInt());

        var entities = node.get("registry").get("entities");
        for (int i = 0; i < entities.size(); i++) {
            var entity = entities.get(i);
            var components = new ArrayList<Component>();

            for (int j = 0; j < entity.get("components").size(); j++) {
                var component = entity.get("components").get(j);
                var type = component.get("__typename").textValue();
                Component c = null;
                switch (type) {
                    case "TagComponent" -> c = mapper.treeToValue(component, TagComponent.class);
                    case "TransformComponent" -> c = mapper.treeToValue(component, TransformComponent.class);
                    case "SpriteRenderComponent" -> c = mapper.treeToValue(component, SpriteRenderComponent.class);
                    case "SceneCameraComponent" -> c = mapper.treeToValue(component, SceneCameraComponent.class);
                    case "ScriptComponent" -> c = mapper.treeToValue(component, ScriptComponent.class);
                }
                components.add(c);
            }
            var e = scene.createVoidEntity();
            for (Component component : components) {
                e.addComponent(component);
            }
            scene.activateEntities(e);
        }

        return scene;
    }
}