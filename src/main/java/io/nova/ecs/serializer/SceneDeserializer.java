package io.nova.ecs.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.nova.core.renderer.texture.TextureLibrary;
import io.nova.ecs.Scene;
import io.nova.ecs.component.Component;
import io.nova.ecs.component.ScriptComponent;
import io.nova.ecs.component.SpriteRendererComponent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

class SceneDeserializer extends StdDeserializer<Scene> {

    public SceneDeserializer() {
        this(Scene.class);
    }

    protected SceneDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Scene deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        var mapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);

        // TODO: Make renderer a static class
        Scene scene = new Scene();
        scene.setViewPortWidth(node.get("viewPortWidth").asInt());
        scene.setViewPortHeight(node.get("viewPortHeight").asInt());

        var entities = node.get("registry").get("entities");
        for (int i = 0; i < entities.size(); i++) {
            var entity = entities.get(i);
            var type = mapper.constructType(new TypeReference<ArrayList<Component>>() {});
            ArrayList<Component> components = mapper.treeToValue(entity.get("components"), type);

            var e = scene.createVoidEntity();
            for (Component component : components) {
                // Bind script to component
                if (component instanceof ScriptComponent) {
                    var scriptableEntityClass = ((ScriptComponent) component).getInstance().getClass();
                    ((ScriptComponent) component).setActivated(false);
                    ((ScriptComponent) e.addComponent(component)).bind(scriptableEntityClass);
                    break;
                }
                if (component instanceof SpriteRendererComponent) {
                    var texture = ((SpriteRendererComponent) component).getTexture();
                    if (Objects.nonNull(texture)) {
                        texture = TextureLibrary.uploadTexture(Path.of(texture.getFilepath()));
                        ((SpriteRendererComponent) component).setTexture(texture);
                    }
                }
                e.addComponent(component);
            }
            scene.activateEntities(e);
        }

        return scene;
    }
}