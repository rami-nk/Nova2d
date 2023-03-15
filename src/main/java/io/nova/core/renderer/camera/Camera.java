package io.nova.core.renderer.camera;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joml.Matrix4f;

public class Camera {
    protected ProjectionType projectionType;
    @JsonIgnore
    protected Matrix4f projection;

    public Camera() {
        this(new Matrix4f());
    }

    public Camera(Matrix4f projection) {
        this.projection = projection;
    }

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public void setProjectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public void setProjection(Matrix4f projection) {
        this.projection = projection;
    }
}