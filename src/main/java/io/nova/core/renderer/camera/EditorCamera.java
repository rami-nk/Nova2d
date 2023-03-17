package io.nova.core.renderer.camera;

import io.nova.event.Event;
import io.nova.event.EventDispatcher;
import io.nova.event.mouse.MouseScrolledEvent;
import io.nova.window.Input;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static io.nova.core.codes.KeyCodes.NV_KEY_LEFT_ALT;
import static io.nova.core.codes.MouseCodes.*;

public class EditorCamera extends Camera {

    private float fov;
    private float aspectRatio;
    private float nearClip, farClip;
    private Matrix4f viewMatrix = new Matrix4f();
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f focalPoint = new Vector3f(0, 0, 0);

    private Vector2f initialMousePosition = new Vector2f();
    private float distance = 10.0f, pitch = 0.0f, yaw = 0.0f;
    private float viewportWidth = 1280.0f, viewportHeight = 720.0f;

    public EditorCamera(float fov, float aspectRatio, float nearClip, float farClip) {
        super(new Matrix4f().perspective((float) Math.toRadians(fov), aspectRatio, nearClip, farClip));
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearClip = nearClip;
        this.farClip = farClip;
        updateProjection();
    }

    public void onUpdate(float deltaTime) {
        if (Input.isKeyPressed(NV_KEY_LEFT_ALT)) {
            var mouse = Input.getMousePosition();
            var delta = new Vector2f(mouse).sub(initialMousePosition).mul(0.003f);
            initialMousePosition = mouse;

            if (Input.isMouseButtonPressed(NV_MOUSE_BUTTON_MIDDLE)) {
                mousePan(new Vector3f(delta, 0));
            } else if (Input.isMouseButtonPressed(NV_MOUSE_BUTTON_LEFT)) {
                mouseRotate(new Vector3f(delta, 0));
            } else if (Input.isKeyPressed(NV_MOUSE_BUTTON_RIGHT)) {
                mouseZoom(delta.y);
            }
        }
        updateView();
    }

    public void onEvent(Event event) {
        var dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(MouseScrolledEvent.class, this::onMouseScrolled);
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setViewportSize(float width, float height) {
        this.viewportWidth = width;
        this.viewportHeight = height;
        updateProjection();
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getViewProjection() {
        return projection.mul(viewMatrix, new Matrix4f());
    }

    public Vector3f getUpDirection() {
        return new Vector3f(0, 1, 0).rotate(getOrientation());
    }

    public Vector3f getRightDirection() {
        return new Vector3f(1, 0, 0).rotate(getOrientation());
    }

    public Vector3f getForwardDirection() {
        return new Vector3f(0, 0, -1).rotate(getOrientation());
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Quaternionf getOrientation() {
        return new Quaternionf()
                .rotateX((float) Math.toRadians(pitch))
                .rotateY((float) Math.toRadians(yaw));
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    private void updateProjection() {
        aspectRatio = viewportWidth / viewportHeight;
        projection = new Matrix4f().perspective((float) Math.toRadians(fov), aspectRatio, nearClip, farClip);
    }

    private void updateView() {
        position = calculatePosition();
        var orientation = getOrientation();

        viewMatrix = new Matrix4f()
                .translate(position)
                .rotate(orientation)
                .invert();
    }

    private boolean onMouseScrolled(MouseScrolledEvent event) {
        float delta = event.getYOffset() * 0.1f;
        mouseZoom(delta);
        updateView();
        return false;
    }

    private void mousePan(Vector3f delta) {
        var speed = panSpeed();
        focalPoint.add(getRightDirection().mul(delta.x * speed.x * distance));
        focalPoint.add(getUpDirection().mul(delta.y * speed.y * distance));
    }

    private void mouseRotate(Vector3f delta) {
        float yawSign = getUpDirection().y < 0.0f ? -1.0f : 1.0f;
        yaw += yawSign * delta.x * rotationSpeed();
        pitch += delta.y * rotationSpeed();
    }

    private void mouseZoom(float delta) {
        distance -= delta * zoomSpeed();
        if (distance < 1.0f) {
            focalPoint.add(getForwardDirection());
            distance = 1.0f;
        }
    }

    private Vector3f calculatePosition() {
        return focalPoint.sub(getForwardDirection().mul(distance), new Vector3f());
    }

    private Vector2f panSpeed() {
        float x = Math.min(viewportWidth / 1000.0f, 2.4f);
        float xFactor = 0.0366f * (x * x) - 0.1778f * x + 0.3021f;

        float y = Math.min(viewportHeight / 1000.0f, 2.4f);
        float yFactor = 0.0366f * (y * y) - 0.1778f * y + 0.3021f;
        return new Vector2f(xFactor, yFactor);
    }

    private float rotationSpeed() {
        return 0.8f;
    }

    private float zoomSpeed() {
        float distance = this.distance * 0.2f;
        distance = Math.max(distance, 0.0f);
        float speed = distance * distance;
        speed = Math.min(speed, 100.0f);
        return speed;
    }
}