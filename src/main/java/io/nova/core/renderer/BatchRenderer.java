package io.nova.core.renderer;

import io.nova.core.GameObject;
import io.nova.core.components.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BatchRenderer {

    private final static int MAX_BATCH_SIZE = 1000;
    private final List<Batch> batches;

    public BatchRenderer() {
        batches = new ArrayList<>();
    }

    public void add(GameObject gameObject) {
        var spriteRenderer = gameObject.getComponent(Sprite.class);
        if (!Objects.isNull(spriteRenderer)) {
            add(spriteRenderer);
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

    public void render() {
        for (var batch : batches) {
            batch.render();
        }
    }
}