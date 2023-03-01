package io.nova.core.application;

import imgui.ImGui;
import io.nova.core.layer.Layer;
import io.nova.core.layer.LayerStack;
import io.nova.core.renderer.*;
import io.nova.core.window.Window;
import io.nova.core.window.WindowFactory;
import io.nova.core.window.WindowProps;
import io.nova.event.Event;
import io.nova.event.EventDispatcher;
import io.nova.event.window.WindowClosedEvent;
import io.nova.event.window.WindowResizeEvent;
import io.nova.imgui.ImGuiLayer;
import io.nova.opengl.renderer.OpenGLIndexBuffer;
import io.nova.opengl.renderer.OpenGLVertexArray;
import io.nova.opengl.renderer.OpenGLVertexBuffer;
import io.nova.opengl.renderer.OpenGLVertexBufferLayout;
import io.nova.utils.ShaderProvider;
import io.nova.utils.Time;
import io.nova.window.Input;
import org.joml.Vector3f;

import java.util.Objects;

import static io.nova.core.codes.KeyCodes.NV_KEY_ESCAPE;

public class Application {

    private ApplicationSpecification specification;
    private static Application application;
    private Window window;
    private boolean running;
    private LayerStack layerStack;

    private Vector3f color;
    private Layer menuLayer;
    private ImGuiLayer imGuiLayer;
    private Renderer renderer;

    private OrthographicCamera camera;

    private Application() {

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

        renderer = Renderer.create();
        color = new Vector3f(0.5f, 0.5f, 0.5f);

        camera = new OrthographicCamera(-1.0f, 1.0f, -1.0f, 1.0f);

        window = WindowFactory.create(new WindowProps());
        window.setEventCallback(this::onEvent);

        layerStack = new LayerStack();
        imGuiLayer = new ImGuiLayer();
        pushOverlay(imGuiLayer);

        // Register Scenes
//        menuLayer = new ClearColorScene();
//        pushLayer(menuLayer);

        isRunning(true);

        float[] vertices = {
                -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
        };
        int[] elementArray = {0, 1, 2, 2, 3, 0};

        vertexArray = new OpenGLVertexArray();
        VertexBuffer vertexBuffer = new OpenGLVertexBuffer(vertices);
        var indexBuffer = new OpenGLIndexBuffer(elementArray);

        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(4);
        vertexArray.addBuffer(vertexBuffer, layout);
        vertexArray.setIndexBuffer(indexBuffer);
        shader = ShaderProvider.getOrElseUploadShader("simple.glsl");
    }

    private Shader shader;
    private VertexArray vertexArray;

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

            renderer.setClearColor(color.x, color.y, color.z, 0.0f);
            renderer.clear();

            for (var layer : layerStack.getLayers()) {
                layer.onUpdate();
            }

            camera.setPosition(new Vector3f(0.5f, 0.5f, 0));

            renderer.beginScene(camera);
            {
                renderer.draw(vertexArray, shader);
            }
            renderer.endScene();

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