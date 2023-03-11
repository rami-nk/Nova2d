import imgui.ImGui;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.RendererFactory;
import io.nova.core.renderer.camera.OrthographicCameraController;
import io.nova.ecs.Scene;
import io.nova.ecs.component.CameraComponent;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.Entity;
import io.nova.event.Event;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class EcsLayer extends Layer {

    private Scene scene;
    private Renderer renderer;
    private OrthographicCameraController cameraController;
    private Entity entity;
    private Entity primaryCamera;
    private Entity secondaryCamera;
    private boolean primary = true;
    private float[] cameraPosition = new float[3];

    public void onAttach() {
        cameraController = new OrthographicCameraController(16.0f / 9.0f, true);
        renderer = RendererFactory.create();
        scene = new Scene(renderer);

        entity = scene.createEntity();
        entity.addComponent(new SpriteRenderComponent());

        primaryCamera = scene.createEntity();
        primaryCamera.addComponent(new CameraComponent());
        primaryCamera.getComponent(CameraComponent.class).setPrimary(primary);

        secondaryCamera = scene.createEntity();
        secondaryCamera.addComponent(new CameraComponent(new Matrix4f().ortho(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f)));
        secondaryCamera.getComponent(CameraComponent.class).setPrimary(!primary);

        scene.activateEntities(entity, primaryCamera, secondaryCamera);
    }

    public void onDetach() {
        scene.dispose();
    }

    public void onUpdate(float deltaTime) {
        cameraController.onUpdate(deltaTime);

        renderer.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        renderer.clear();

        scene.onUpdate(deltaTime);
    }

    public void onImGuiRender() {
        ImGui.text(entity.getComponent(TagComponent.class).getTag());
        if (ImGui.checkbox(primary ? "Primary" : "Secondary", primary)) {
            primary = !primary;
            primaryCamera.getComponent(CameraComponent.class).setPrimary(primary);
            secondaryCamera.getComponent(CameraComponent.class).setPrimary(!primary);
        }

        ImGui.dragFloat3("Camera Position", cameraPosition);
        System.out.println(cameraPosition[0] + ", " + cameraPosition[1] + ", " + cameraPosition[2]);
        var transformComponent = primaryCamera.getComponent(TransformComponent.class);
        transformComponent.setTransform(new Matrix4f().translate(new Vector3f(cameraPosition), new Matrix4f()));
    }

    public void onEvent(Event event) {
        cameraController.onEvent(event);
    }
}