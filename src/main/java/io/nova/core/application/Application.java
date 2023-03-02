package io.nova.core.application;

import imgui.ImGui;
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
import org.joml.Vector3f;

import static io.nova.core.codes.KeyCodes.NV_KEY_ESCAPE;

public class Application {

    private static Application application;
    private final ApplicationSpecification specification;
    private final Window window;
    private boolean running;
    private final LayerStack layerStack;
    private final ImGuiLayer imGuiLayer;

    public Application(ApplicationSpecification specification) {
        application = this;
        this.specification = specification;

        if (specification.getWorkingDirectory().isEmpty()) {
            var currentDir = System.getProperty("user.dir");
            specification.setWorkingDirectory(currentDir);
        }

        window = WindowFactory.create(new WindowProps());
        window.setEventCallback(this::onEvent);

        layerStack = new LayerStack();
        imGuiLayer = new ImGuiLayer();
        pushOverlay(imGuiLayer);

        isRunning(true);
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

    public static Application getInstance() {
        return application;
    }

    private boolean onWindowClosed(WindowClosedEvent event) {
        Application.getInstance().isRunning(false);
        return true;
    }

    private boolean onWindowResize(WindowResizeEvent event) {
        return true;
    }

    public static Window getWindow() {
        return getInstance().window;
    }

    public void run() {
        double startTime = Time.getElapsedTimeSinceApplicationStartInSeconds();
        double endTime;
        double deltaTime = -1;

        while (running && !Input.isKeyPressed(NV_KEY_ESCAPE)) {

            for (var layer : layerStack.getLayers()) {
                layer.onUpdate();
            }

            imGuiLayer.startFrame();
            ImGui.begin("window");
            {
                for (var layer : layerStack.getLayers()) {
                    layer.onImGuiRender();
                }
            }
            ImGui.end();
            imGuiLayer.endFrame();

            window.onUpdate();

            endTime = Time.getElapsedTimeSinceApplicationStartInSeconds();
            deltaTime = endTime - startTime;
            startTime = endTime;
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