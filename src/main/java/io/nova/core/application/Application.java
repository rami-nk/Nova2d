package io.nova.core.application;

import imgui.ImGui;
import io.nova.core.imgui.ImGuiLayer;
import io.nova.core.renderer.Renderer;
import io.nova.core.scene.*;
import io.nova.core.utils.Time;
import io.nova.core.window.Window;
import io.nova.core.window.WindowFactory;
import io.nova.core.window.WindowProps;
import io.nova.event.Event;
import io.nova.event.EventDispatcher;
import io.nova.event.window.WindowClosedEvent;
import io.nova.event.window.WindowResizeEvent;
import org.joml.Vector3f;

import java.util.Objects;

public class Application {

    private ApplicationSpecification specification;
    private static Application application;
    private Window window;
    private boolean running;
    private Vector3f color;
    private MenuScene menuScene;
    private ImGuiLayer imGuiLayer;

    private Application() {

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

        isRunning(true);

        imGuiLayer = new ImGuiLayer(window.getNativeWindow());
        imGuiLayer.init();

        // Register Scenes
        menuScene = new MenuScene(null);
        menuScene.setCurrentScene(menuScene);

        menuScene.registerScene("Clear color", ClearColorScene.class);
        menuScene.registerScene("Zoom texture", ZoomTextureScene.class);
        menuScene.registerScene("Simple colored square", SimpleColoredSquareScene.class);
        menuScene.registerScene("OpenGL logo", OpenGlLogoScene.class);
        menuScene.registerScene("Nova2d logo", Nova2dLogoScene.class);
        menuScene.registerScene("Batch", BatchScene.class);
        menuScene.registerScene("Sprite sheet", SpriteSheetScene.class);
    }

    public static Application getInstance() {
        if (Objects.isNull(application)) {
            application = new Application();
        }
        return application;
    }

    public static void onEvent(Event event) {
        var dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(WindowClosedEvent.class, Application::onWindowClosed);
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

            Renderer.setClearColor(color.x, color.y, color.z, 0.0f);
            Renderer.clear();

            imGuiLayer.startFrame();

            var currentScene = menuScene.getCurrentScene();
            currentScene.update(deltaTime);
            currentScene.render();

            ImGui.begin("Nova2d");
            if (currentScene != menuScene && ImGui.button("<-")) {
                menuScene.setCurrentScene(menuScene);
            }
            currentScene.imGuiRender();
            ImGui.end();

            imGuiLayer.endFrame();

            window.onUpdate();

            endTime = Time.getElapsedTimeSinceApplicationStartInSeconds();
            deltaTime = endTime - startTime;
            startTime = endTime;
        }

        shutdown();
    }

    private void shutdown() {
        window.shutdown();
        imGuiLayer.dispose();
    }

    private void isRunning(boolean running) {
        this.running = running;
    }
}