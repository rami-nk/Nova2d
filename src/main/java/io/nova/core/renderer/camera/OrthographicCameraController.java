package io.nova.core.renderer.camera;

import io.nova.event.Event;
import io.nova.event.EventDispatcher;
import io.nova.event.mouse.MouseScrolledEvent;
import io.nova.event.window.WindowResizeEvent;
import io.nova.window.Input;
import org.joml.Vector3f;

import static io.nova.core.codes.KeyCodes.*;

public class OrthographicCameraController {

    private final OrthographicCamera camera;
    private final boolean enableRotation;
    private final Vector3f position;
    private float aspectRatio;
    private float zoomLevel;
    private float rotation;
    private float cameraTranslationSpeed;
    private float cameraRotationSpeed;

    public OrthographicCameraController(float aspectRatio) {
        this(aspectRatio, false);
    }

    public OrthographicCameraController(float aspectRatio, boolean enableRotation) {
        this.enableRotation = enableRotation;
        this.aspectRatio = aspectRatio;
        this.zoomLevel = 1.0f;
        this.camera = new OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
        this.position = new Vector3f();
        this.rotation = 0.0f;
        this.cameraTranslationSpeed = 1.0f;
        this.cameraRotationSpeed = 180.0f;
    }

    public void onUpdate(float deltaTime) {
        if (Input.isKeyPressed(NV_KEY_A)) {
            position.x -= cameraTranslationSpeed * deltaTime;
        } else if (Input.isKeyPressed(NV_KEY_D)) {
            position.x += cameraTranslationSpeed * deltaTime;
        }

        if (Input.isKeyPressed(NV_KEY_W)) {
            position.y += cameraTranslationSpeed * deltaTime;
        } else if (Input.isKeyPressed(NV_KEY_S)) {
            position.y -= cameraTranslationSpeed * deltaTime;
        }

        if (enableRotation) {
            if (Input.isKeyPressed(NV_KEY_Q)) {
                rotation += cameraRotationSpeed * deltaTime;
            }
            if (Input.isKeyPressed(NV_KEY_E)) {
                rotation -= cameraRotationSpeed * deltaTime;
            }
            camera.setRotation(rotation);
        }

        camera.setPosition(position);
        cameraTranslationSpeed = zoomLevel;
    }

    public void onEvent(Event event) {
        var dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(MouseScrolledEvent.class, this::onMouseScrolled);
        dispatcher.dispatch(WindowResizeEvent.class, this::onWindowResized);
    }

    private boolean onMouseScrolled(MouseScrolledEvent event) {
        zoomLevel -= event.getYOffset() * 0.25;
        zoomLevel = Math.max(zoomLevel, 0.25f);
        calculateView();
        return false;
    }

    private boolean onWindowResized(WindowResizeEvent event) {
        aspectRatio = (float) event.getWidth() / (float) event.getHeight();
        calculateView();
        return false;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCameraTranslationSpeed(float cameraTranslationSpeed) {
        this.cameraTranslationSpeed = cameraTranslationSpeed;
    }

    public void setCameraRotationSpeed(float cameraRotationSpeed) {
        this.cameraRotationSpeed = cameraRotationSpeed;
    }

    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        calculateView();
    }

    private void calculateView() {
        camera.setProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
    }
}