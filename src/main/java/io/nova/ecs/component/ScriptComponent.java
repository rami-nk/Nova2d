package io.nova.ecs.component;

import io.nova.ecs.entity.ScriptableEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ScriptComponent extends Component {

    private Consumer<ScriptableEntity> onCreateFunction;
    private Consumer<ScriptableEntity> onDestroyFunction;
    private BiConsumer<ScriptableEntity, Float> onUpdateFunction;

    private ScriptableEntity instance;
    private Class<? extends ScriptableEntity> clazz;

    public <T extends ScriptableEntity> void bind(Class<T> clazz) {
        onCreateFunction = ScriptableEntity::onCreate;
        onDestroyFunction = ScriptableEntity::onDestroy;
        onUpdateFunction = ScriptableEntity::onUpdate;
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
        onCreateFunction.accept(instance);
    }

    public void onDestroy() {
        onDestroyFunction.accept(instance);
    }

    public void onUpdate(float delta) {
        onUpdateFunction.accept(instance, delta);
    }

    public void setInstanceEntity() {
        instance.setEntity(getEntity());
    }

    public ScriptableEntity getInstance() {
        return instance;
    }
}