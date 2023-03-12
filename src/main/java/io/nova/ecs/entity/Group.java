package io.nova.ecs.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group {

    private final Set<Class<?>> types = new HashSet<>();

    private Group() {}

    public static Group create(Class<?>... types) {
        Group family = new Group();
        family.types.addAll(Arrays.asList(types));
        return family;
    }

    public boolean isMember(Entity e) {
        for (Class<?> type : types) {
            if (!e.hasComponent(type)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + types.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (Objects.isNull(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        return types.equals(other.types);
    }
}