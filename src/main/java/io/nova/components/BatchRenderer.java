package io.nova.components;

import io.nova.components.GameObject;
import io.nova.components.Sprite;
import io.nova.core.renderer.Camera;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import io.nova.opengl.renderer.Batch;

public class BatchRenderer {

    private final static int MAX_BATCH_SIZE = 1000;
    private final List<Batch> batches;

    public BatchRenderer() {
        batches = new ArrayList<>();
    }

    public void add(GameObject gameObject) {
        var sprite = gameObject.getComponent(Sprite.class);
        if (!Objects.isNull(sprite)) {
            add(sprite);
        }
    }

    private void add(Sprite sprite) {
        boolean added = false;
        for (var batch : batches) {
            if (batch.hasRoom()) {
                batch.addSprite(sprite);
                added = true;
                break;
            }
        }

        if (!added) {
            var batch = new Batch(MAX_BATCH_SIZE);
            batch.start();
            batches.add(batch);
            batch.addSprite(sprite);
        }
    }

    public void render(Camera camera) {
        for (var batch : batches) {
            batch.render(camera);
        }
    }
}