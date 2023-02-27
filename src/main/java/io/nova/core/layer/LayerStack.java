package io.nova.core.layer;

import java.util.*;

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

    public static void main(String[] args) {
        var stack = new LayerStack();
        stack.pushOverlay(new TestLayer("Overlay"));
        stack.pushLayer(new TestLayer("Test1"));
        stack.pushLayer(new TestLayer("Test2"));
        stack.pushLayer(new TestLayer("Test3"));

        for (var layer : stack.getLayers()) {
            System.out.println(layer.getName());
        }
    }
}

class TestLayer extends Layer {
    public TestLayer(String name) {
        this.name = name;
    }
}