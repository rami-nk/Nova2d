package io.nova.core.application;

import imgui.ImGui;
import io.nova.core.imgui.ImGuiLayer;
import io.nova.core.listener.KeyListener;
import io.nova.core.renderer.Renderer;
import io.nova.core.scene.*;
import io.nova.core.utils.Time;
import io.nova.core.window.Window;
import io.nova.core.window.WindowFactory;
import io.nova.core.window.WindowProps;
import io.nova.event.window.WindowClosedEvent;
import io.nova.event.window.WindowResizeEvent;
import org.joml.Vector3f;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;

public class Application {

    private ApplicationSpecification specification;
    private static Application application;
    private Window window;
    private boolean running;
    private Vector3f color;
    private MenuScene menuScene;
    private Scene currentScene;
    private ImGuiLayer imGuiLayer;
    public Application(ApplicationSpecification specification) {
        this.specification = specification;

        application = this;
        if (specification != null) {
            if (specification.getWorkingDirectory().isEmpty()) {
                var currentDir = System.getProperty("user.dir");
                specification.setWorkingDirectory(currentDir);
            }
        }

        color = new Vector3f(0.5f, 0.5f, 0.5f);

        window = WindowFactory.create(new WindowProps());
        running = true;

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
            application = new Application(null);
        }
        return application;
    }

    public static Window getWindow() {
        return getInstance().window;
    }

    private boolean onWindowClosed(WindowClosedEvent event) {
        return false;
    }

    private boolean onWindowResize(WindowResizeEvent event) {
        return false;
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

            if (KeyListener.isKeyPressed(GLFW_KEY_BACKSPACE) && currentScene != menuScene) {
                menuScene.setCurrentScene(menuScene);
            }

            imGuiLayer.endFrame();

            window.onUpdate();

            endTime = Time.getElapsedTimeSinceApplicationStartInSeconds();
            deltaTime = endTime - startTime;
            startTime = endTime;
        }

        window.shutdown();
        // TODO: Create shutdown method
        imGuiLayer.dispose();
    }

    public void onEvent() {

    }

    public long getWindowPointer() {
        return window.getNativeWindow();
    }
}