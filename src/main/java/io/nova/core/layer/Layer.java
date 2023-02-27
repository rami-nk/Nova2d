package io.nova.core.layer;

import io.nova.event.Event;

public abstract class Layer {

    protected String name;

    public void onAttach() { }

    public void onDetach() { }

    public void onUpdate() { }

    public void onImGuiRender() { }

    public void onEvent(Event event) { }

    public String getName() {
        return this.name;
    }
}