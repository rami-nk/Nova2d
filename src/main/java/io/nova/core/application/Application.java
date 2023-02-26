package io.nova.core.application;

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
import io.nova.scenes.*;
import io.nova.utils.Time;
import org.joml.Vector3f;

import java.util.Iterator;
import java.util.Objects;

public class Application {

    private ApplicationSpecification specification;
    private static Application application;
    private Window window;
    private boolean running;
    private LayerStack layerStack;

    private Vector3f color;
    private MenuScene menuScene;

    private Application() {

    }

    public static void onEvent(Event event) {
        var dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(WindowClosedEvent.class, Application::onWindowClosed);
        dispatcher.dispatch(WindowResizeEvent.class, Application::onWindowResize);

        for (Iterator<Layer> it = application.layerStack.getLayers().descendingIterator(); it.hasNext(); ) {
            var layer = it.next();
            if (event.isHandled()) {
                break;
            }
            layer.onEvent(event);
        }
    }

    public static Application getInstance() {
        if (Objects.isNull(application)) {
            application = new Application();
        }
        return application;
    }

    public void init(ApplicationSpecification specification) {
        this.specification = specification;

        if (specification.getWorkingDirectory().isEmpty()) {
            var currentDir = System.getProperty("user.dir");
            specification.setWorkingDirectory(currentDir);
        }

        color = new Vector3f(0.5f, 0.5f, 0.5f);

        window = WindowFactory.create(new WindowProps());
        window.setEventCallback(Application::onEvent);

        layerStack = new LayerStack();
        pushOverlay(new ImGuiLayer());

        // Register Scenes
        menuScene = new MenuScene();
        menuScene.setCurrentScene(menuScene);

        menuScene.registerScene("Clear color", ClearColorScene.class);
        menuScene.registerScene("Zoom texture", ZoomTextureScene.class);
        menuScene.registerScene("Simple colored square", SimpleColoredSquareScene.class);
        menuScene.registerScene("OpenGL logo", OpenGlLogoScene.class);
        menuScene.registerScene("Nova2d logo", Nova2dLogoScene.class);
        menuScene.registerScene("Batch", BatchScene.class);
        menuScene.registerScene("Sprite sheet", SpriteSheetScene.class);

        isRunning(true);
    }

    private static boolean onWindowClosed(WindowClosedEvent event) {
        Application.getInstance().isRunning(false);
        return true;
    }

    private static boolean onWindowResize(WindowResizeEvent event) {
        return true;
    }

    public static Window getWindow() {
        return getInstance().window;
    }

    public void run() {
        double startTime = Time.getElapsedTimeSinceApplicationStartInSeconds();
        double endTime;
        double deltaTime = -1;

        while (running) {

            for (var layer : layerStack.getLayers()) {
                layer.onUpdate();
            }

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