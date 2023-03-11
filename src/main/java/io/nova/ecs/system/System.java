package io.nova.ecs.system;

import io.nova.ecs.Registry;

public abstract class System {

    private boolean enabled = true;
    private Registry registry;

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean b) {
        if (enabled == b) return;
        enabled = b;
        enabledStateChanged();
    }

    public final Registry getRegistry() {
        return registry;
    }

    public final void setRegistry(Registry e) {
        registry = e;
    }

    public void addedToRegistry(Registry e) {}

    public void removedFromRegistry(Registry e) {}

    public void update(double dt) {}

    protected void enabledStateChanged() {}
}