package io.nova.ecs.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.nova.ecs.component.Component;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
public abstract class ScriptableEntity {

    private Entity entity;

    public <T extends Component> T getComponent(Class<T> clazz) {
        return entity.getComponent(clazz);
    }

    public void setEntity(Entity e) {
        entity = e;
    }

    public void onCreate() {}

    public void onDestroy() {}

    public void onUpdate(float deltaTime) {}
}