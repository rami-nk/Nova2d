package io.nova.ecs.component;

import java.util.UUID;

public class TagComponent extends Component {
    private final String tag;

    public TagComponent() {
        this(generateId());
    }

    public TagComponent(String tag) {
        this.tag = tag;
    }

    private static String generateId() {
        var hex = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 8);
        return "Entity@" + hex;
    }

    public String getTag() {
        return tag;
    }
}