package io.nova.core.application;

import io.nova.core.codes.KeyCode;
import io.nova.core.layer.Layer;
import io.nova.core.layer.LayerStack;
import io.nova.core.window.Window;
import io.nova.core.window.WindowFactory;
import io.nova.core.window.WindowProps;
import io.nova.event.Event;
import io.nova.event.EventDispatcher;
import io.nova.event.window.WindowClosedEvent;
import io.nova.event.window.WindowResizeEvent;
import io.nova.imgui.ImGuiLayer;
import io.nova.utils.Time;
import io.nova.window.Input;

public class Application {

    private static Application application;
    private final ApplicationSpecification specification;
    private final Window window;
    private final LayerStack layerStack;
    private final ImGuiLayer imGuiLayer;
    private boolean running;
    private boolean minimized;

    public Application(ApplicationSpecification specification) {
        application = this;
        this.specification = specification;

        if (specification.getWorkingDirectory().isEmpty()) {
            var currentDir = System.getProperty("user.dir");
            specification.setWorkingDirectory(currentDir);
        }

        this.window = WindowFactory.create(new WindowProps(specification.getName()));
        this.window.setEventCallback(this::onEvent);

        this.layerStack = new LayerStack();
        this.imGuiLayer = new ImGuiLayer();
        pushOverlay(this.imGuiLayer);

        isRunning(true);
        minimized = false;
    }

    public static Application getInstance() {
        return application;
    }

    public static Window getWindow() {
        return getInstance().window;
    }

    public void onEvent(Event event) {
        var dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(WindowClosedEvent.class, this::onWindowClosed);
        dispatcher.dispatch(WindowResizeEvent.class, this::onWindowResize);

        for (int i = application.layerStack.getLayers().size(); i-- > 0; ) {
            if (event.isHandled()) {
                break;
            }
            var layer = application.layerStack.getLayers().get(i);
            layer.onEvent(event);
        }
    }

    private boolean onWindowClosed(WindowClosedEvent event) {
        Application.getInstance().isRunning(false);
        return true;
    }

    private boolean onWindowResize(WindowResizeEvent event) {
        if (event.getWidth() == 0 || event.getHeight() == 0) {
            minimized = true;
            return false;
        }
        minimized = false;
        return false;
    }

    public void run() {
        float endTime = 0;

        while (running && !Input.isKeyPressed(KeyCode.KEY_ESCAPE)) {

            float startTime = Time.getElapsedTimeSinceApplicationStartInSeconds();
            float deltaTime = startTime - endTime;
            endTime = startTime;

            if (!minimized) {
                for (var layer : layerStack.getLayers()) {
                    layer.onUpdate(deltaTime);
                }
            }

            imGuiLayer.startFrame();
            {
                for (var layer : layerStack.getLayers()) {
                    layer.onImGuiRender();
                }
            }
            imGuiLayer.endFrame();

            window.onUpdate();
        }
        shutdown();
    }

    public void pushOverlay(Layer layer) {
        layerStack.pushOverlay(layer);
        layer.onAttach();
    }

    public void pushLayer(Layer layer) {
        layerStack.pushLayer(layer);
        layer.onAttach();
    }

    public void close() {
        running = false;
    }

    private void shutdown() {
        window.shutdown();
        for (var layer : layerStack.getLayers()) {
            layer.onDetach();
        }
    }

    private void isRunning(boolean running) {
        this.running = running;
    }
}