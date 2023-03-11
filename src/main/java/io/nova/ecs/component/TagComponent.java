package io.nova.ecs.component;

import java.util.UUID;

public class TagComponent extends Component {
    private final String tag;

    public TagComponent() {
        this(String.valueOf(UUID.randomUUID()));
    }

    public TagComponent(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}