package io.nova.ecs.component;

import io.nova.ecs.Registry;
import io.nova.ecs.entity.Entity;

public abstract class Component {

    private Entity entity;
    private boolean activated;

    public final Entity getEntity() {
        return entity;
    }

    public final void setEntity(Entity e) {
        entity = e;
    }

    final protected <T> T getSystem(Class<T> clazz) throws IllegalArgumentException,
            NullPointerException {
        return entity.getRegistry().getSystem(clazz);
    }

    final protected <T> T getComponent(Class<T> clazz)
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