package io.nova.core.layer;

import java.util.Deque;
import java.util.LinkedList;

public class LayerStack {

    private final Deque<Layer> layers;
    private final Deque<Layer> overlays;

    public LayerStack() {
        layers = new LinkedList<>();
        overlays = new LinkedList<>();
    }

    public void pushLayer(Layer layer) {
        layers.push(layer);
    }

    public void pushOverlay(Layer layer) {
        overlays.push(layer);
        layer.onAttach();
    }

    public void popLayer() {
        layers.pop();
    }

    public void popOverlay() {
        overlays.pop();
    }

    public Deque<Layer> getLayers() {
        var returnStack = new LinkedList<Layer>();
        returnStack.addAll(layers);
        returnStack.addAll(overlays);
        return returnStack;
    }
}