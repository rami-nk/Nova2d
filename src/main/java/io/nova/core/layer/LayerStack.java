package io.nova.core.layer;

import java.util.Stack;

public class LayerStack {

    private final Stack<Layer> layers;
    private final Stack<Layer> overlays;

    public LayerStack() {
        layers = new Stack<>();
        overlays = new Stack<>();
    }

    public void pushLayer(Layer layer) {
        layers.push(layer);
    }

    public void pushOverlay(Layer layer) {
        overlays.push(layer);
    }

    public void popLayer() {
        layers.pop();
    }

    public void popOverlay() {
        overlays.pop();
    }

    public Stack<Layer> getLayers() {
        var returnStack = new Stack<Layer>();
        returnStack.addAll(layers);
        returnStack.addAll(overlays);
        return returnStack;
    }
}