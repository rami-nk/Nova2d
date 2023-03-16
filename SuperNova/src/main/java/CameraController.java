import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.ScriptableEntity;
import io.nova.window.Input;

import static io.nova.core.codes.KeyCodes.*;

public class CameraController extends ScriptableEntity {
    @Override
    public void onUpdate(float deltaTime) {
        var transformComponent = getComponent(TransformComponent.class);
        var speed = 5.0f * deltaTime;

        if (Input.isKeyPressed(NV_KEY_A)) {
            transformComponent.translate(-speed, 0);
        } else if (Input.isKeyPressed(NV_KEY_D)) {
            transformComponent.translate(speed, 0);
        }

        if (Input.isKeyPressed(NV_KEY_W)) {
            transformComponent.translate(0, speed);
        } else if (Input.isKeyPressed(NV_KEY_S)) {
            transformComponent.translate(0, -speed);
        }
    }
}