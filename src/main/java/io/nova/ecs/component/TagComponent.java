package io.nova.ecs.component;

public class TagComponent extends Component {
    private String tag;

    public TagComponent() {
        this("");
    }

    public TagComponent(String tag) {
        this.tag = isInvalid(tag) ? "Unnamed Entity" : tag;
    }

    private static boolean isInvalid(String tag) {
        return tag == null || tag.isEmpty() || tag.isBlank();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}