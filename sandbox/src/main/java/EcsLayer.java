import io.nova.core.layer.Layer;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.RendererFactory;
import io.nova.core.renderer.camera.OrthographicCameraController;
import io.nova.ecs.Scene;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.component.TransformComponent;
import io.nova.event.Event;
import io.nova.utils.Profiler;

public class EcsLayer extends Layer {

    private Scene scene;
    private Renderer renderer;
    private OrthographicCameraController cameraController;

    public void onAttach() {
        cameraController = new OrthographicCameraController(16.0f / 9.0f, true);
        renderer = RendererFactory.create();
        scene = new Scene(renderer);

        var entity = scene.createEntity();
        entity.addComponent(new SpriteRenderComponent());
        entity.addComponent(new TransformComponent());
        scene.activateEntities(entity);
    }

    public void onDetach() {
        scene.dispose();
    }

    public void onUpdate(float deltaTime) {
        cameraController.onUpdate(deltaTime);

        Profiler.profile(() -> {
            renderer.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            renderer.clear();

            renderer.beginScene(cameraController.getCamera());
            scene.onUpdate(deltaTime);
            renderer.endScene();
        });
    }

    public void onImGuiRender() {}

    public void onEvent(Event event) {
        cameraController.onEvent(event);
    }
}