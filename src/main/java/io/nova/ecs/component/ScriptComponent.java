package io.nova.ecs.component;

import io.nova.ecs.entity.ScriptableEntity;

import java.lang.reflect.InvocationTargetException;

public class ScriptComponent extends Component {

    private ScriptableEntity instance;
    private Class<? extends ScriptableEntity> clazz;

    public <T extends ScriptableEntity> void bind(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void createInstance() {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        try {
            instance = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCreate() {
        instance.onCreate();
    }

    public void onDestroy() {
        instance.onDestroy();
    }

    public void onUpdate(float deltaTime) {
        instance.onUpdate(deltaTime);
    }

    public void setInstanceEntity() {
        instance.setEntity(getEntity());
    }

    public ScriptableEntity getInstance() {
        return instance;
    }
}