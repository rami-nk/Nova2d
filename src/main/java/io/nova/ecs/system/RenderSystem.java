package io.nova.ecs.system;

import io.nova.core.renderer.Renderer;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.entity.Group;

public class RenderSystem extends IteratingSystem {
    private final Renderer renderer;

    public RenderSystem(Renderer renderer) {
        super(Group.create(SpriteRenderComponent.class, TransformComponent.class));
        setEnabled(true);
        this.renderer = renderer;
    }

    @Override
    protected void processEntity(Entity e, double dt) {
        var transform = e.getComponent(TransformComponent.class);
        var sprite = e.getComponent(SpriteRenderComponent.class);
        renderer.drawQuad(transform.getTransform(), sprite.getColor());
    }
}
