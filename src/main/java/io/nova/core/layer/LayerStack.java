package io.nova.core.layer;

import java.util.ArrayList;
import java.util.List;

public class LayerStack {

    private final List<Layer> layers;
    private final List<Layer> overlays;

    public LayerStack() {
        layers = new ArrayList<>();
        overlays = new ArrayList<>();
    }

    public void pushLayer(Layer layer) {
        layers.add(layer);
    }

    public void pushOverlay(Layer layer) {
        overlays.add(layer);
    }

    public void popLayer() {
        layers.remove(layers.size() - 1);
    }

    public void popOverlay() {
        overlays.remove(overlays.size() - 1);
    }

    public List<Layer> getLayers() {
        var returnStack = new ArrayList<Layer>();
        returnStack.addAll(layers);
        returnStack.addAll(overlays);
        return returnStack;
    }
}