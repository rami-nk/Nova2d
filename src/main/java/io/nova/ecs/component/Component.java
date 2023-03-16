package io.nova.ecs.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.nova.ecs.Registry;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.system.EcSystem;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class Component {

    @JsonIgnore
    private Entity entity;
    @JsonIgnore
    private boolean activated;

    public final Entity getEntity() {
        return entity;
    }

    public final void setEntity(Entity e) {
        entity = e;
    }

    final protected <T extends EcSystem> T getSystem(Class<T> clazz) throws IllegalArgumentException,
            NullPointerException {
        return entity.getRegistry().getSystem(clazz);
    }

    final protected <T extends Component> T getComponent(Class<T> clazz)
            throws IllegalArgumentException, NullPointerException {
        return entity.getComponent(clazz);
    }

    final protected Registry getRegistry() throws NullPointerException {
        return entity.getRegistry();
    }

    public final boolean isActivated() {
        return activated;
    }

    protected void activate() {}

    public final void activateInternal() {
        if (isActivated()) {
            throw new IllegalStateException("component already activated");
        }
        activate();
        activated = true;
    }

    protected void deactivate() {}

    public final void deactivateInternal() {
        if (!isActivated()) {
            throw new IllegalStateException("component not activated");
        }
        deactivate();
        activated = false;
    }
}