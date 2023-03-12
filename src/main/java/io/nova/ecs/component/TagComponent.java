package io.nova.ecs.component;

import java.util.UUID;

public class TagComponent extends Component {
    private final String tag;

    public TagComponent() {
        this(generateId());
    }

    public TagComponent(String tag) {
        this.tag = isInvalid(tag) ? generateId() : tag;
    }

    private static boolean isInvalid(String tag) {
        return tag == null || tag.isEmpty() || tag.isBlank();
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