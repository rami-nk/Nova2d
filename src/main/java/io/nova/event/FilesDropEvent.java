package io.nova.event;

public class FilesDropEvent extends Event {
    private final String[] paths;
    private final int count;

    public FilesDropEvent() {
        this(0, new String[0]);
    }

    public FilesDropEvent(int count, String[] paths) {
        this.count = count;
        this.paths = paths;
    }

    public String[] getPaths() {
        return paths;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategory.Input.get();
    }
}