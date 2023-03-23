package io.nova.ecs.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jbox2d.dynamics.Body;

public class RigidBodyComponent extends Component {
    private BodyType bodyType = BodyType.STATIC;
    private boolean fixedRotation;
    @JsonIgnore
    private Body runtimeBody;

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public boolean isFixedRotation() {
        return fixedRotation;
    }

    public void setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
    }

    public Body getRuntimeBody() {
        return runtimeBody;
    }

    public void setRuntimeBody(Body runtimeBody) {
        this.runtimeBody = runtimeBody;
    }

    public enum BodyType {
        STATIC,
        DYNAMIC,
        KINEMATIC
    }
}