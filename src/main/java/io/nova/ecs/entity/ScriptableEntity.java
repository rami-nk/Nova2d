package io.nova.ecs.entity;

import io.nova.ecs.component.Component;

public abstract class ScriptableEntity {

    private Entity entity;

    public <T extends Component> T getComponent(Class<T> clazz) {
        return entity.getComponent(clazz);
    }

    public void setEntity(Entity e) {
        entity = e;
    }

    public abstract void onCreate();

    public abstract void onDestroy();

    public abstract void onUpdate(float deltaTime);
}