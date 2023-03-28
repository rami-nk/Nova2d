package io.nova.ecs.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jbox2d.dynamics.Fixture;

public class CircleColliderComponent extends Component {

    @JsonIgnore
    private Fixture runtimeFixture;

    private float[] offset = new float[2];
    private float radius = 0.5f;
    private float density = 1.0f;
    private float friction = 0.3f;
    private float restitution = 0.0f;

    public float[] getOffset() {
        return offset;
    }

    public void setOffset(float[] offset) {
        this.offset = offset;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public Fixture getRuntimeFixture() {
        return runtimeFixture;
    }

    public void setRuntimeFixture(Fixture runtimeFixture) {
        this.runtimeFixture = runtimeFixture;
    }
}