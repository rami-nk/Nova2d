package io.nova.ecs;

import io.nova.ecs.entity.Entity;
import io.nova.ecs.entity.EntityListener;
import io.nova.ecs.entity.Group;
import io.nova.ecs.system.EcSystem;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Registry {

    private final List<Entity> entities = new ArrayList<>();
    private final Map<Group, List<Entity>> views = new HashMap<>();
    private final List<Command> commands = new ArrayList<>();
    private final List<EcSystem> systems = new ArrayList<>();
    private final List<EntityListener> listeners = new CopyOnWriteArrayList<>();
    private final Map<Group, List<EntityListener>> filteredListeners = new HashMap<>();
    private boolean updating;

    public void addEntityListener(EntityListener l, Group group) {
        List<EntityListener> lst = filteredListeners.get(group);
        if (lst == null) {
            lst = new CopyOnWriteArrayList<>();
            filteredListeners.put(group, lst);
        }
        assert !lst.contains(l) : "listener already added " + l;
        lst.add(l);
    }

    public void removeEntityListener(EntityListener l, Group group) {
        List<EntityListener> lst = filteredListeners.get(group);
        if (lst != null) {
            lst.remove(l);
        }
    }

    public void addEntityListener(EntityListener l) {
        assert !listeners.contains(l) : "listener already added " + l;
        listeners.add(l);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity e) {
        if (updating) {
            commands.add(() -> addEntityInternal(e));
        } else {
            addEntityInternal(e);
        }
    }

    public void removeAll() {
        if (updating) {
            commands.add(this::removeAllInternal);
        } else {
            removeAllInternal();
        }
    }

    public void removeEntity(Entity e) {
        if (updating) {
            commands.add(() -> removeEntityInternal(e));
        } else {
            removeEntityInternal(e);
        }
    }

    private void addEntityInternal(Entity e) {
        if (e.getRegistry() != null) {
            throw new IllegalArgumentException(
                    "entity already added to an registry");
        }
        assert !e.isActivated();
        assert !entities.contains(e);

        entities.add(e);
        e.setRegistry(this);
        e.activate();

        addEntityToViews(e);
    }

    public void updateEntity(Entity entity) {
        commands.add(() -> updateEntityInternal(entity));
    }

    private void updateEntityInternal(Entity entity) {
        addEntityToViews(entity);

        // inform listeners
        for (EntityListener l : listeners) {
            l.entityUpdated(entity);
        }

        for (Map.Entry<Group, List<EntityListener>> entry : filteredListeners.entrySet()) {
            if (entry.getKey().isMember(entity)) {
                for (EntityListener l : entry.getValue()) {l.entityUpdated(entity);}
            }
        }
    }

    private void addEntityToViews(Entity e) {
        for (Group group : views.keySet()) {
            if (group.isMember(e)) {
                views.get(group).add(e);
            }
        }
    }

    private void removeEntityInternal(Entity e) {
        if (e.getRegistry() != this) {
            return;
        }
        assert e.isActivated();
        assert entities.contains(e);

        e.deactivate();
        e.setRegistry(null);
        entities.remove(e);

        removeEntityFromViews(e);
    }

    private void removeAllInternal() {
        while (!entities.isEmpty()) {
            removeEntityInternal(entities.get(0));
        }
    }

    private void removeEntityFromViews(Entity e) {
        for (List<Entity> view : views.values()) {
            view.remove(e);
        }
    }

    public void update(double dt) {
        assert !updating;
        updating = true;

        // update systems
        for (EcSystem s : systems) {
            if (s.isEnabled()) {
                s.update(dt);
            }
        }

        // execute pending commands
        for (Command cmd : commands) {
            cmd.execute();
        }
        commands.clear();

        updating = false;
    }

    public List<Entity> getEntities(Group group) {
        List<Entity> view = views.get(group);
        if (Objects.isNull(view)) {
            view = new ArrayList<>();
            views.put(group, view);
            initView(group, view);
        }
        return Collections.unmodifiableList(view);
    }

    private void initView(Group group, List<Entity> view) {
        assert view.isEmpty();
        for (Entity e : entities) {
            if (group.isMember(e)) {
                view.add(e);
            }
        }
    }

    public void addSystem(EcSystem s) throws IllegalStateException,
            IllegalArgumentException {
        if (updating) {
            throw new IllegalStateException("cannot add system while updating");
        }

        if (systems.contains(s)) {
            throw new IllegalArgumentException("system already added");
        }

        s.setRegistry(this);
        systems.add(s);
        s.addedToRegistry(this);
    }

    public <T> T getSystem(Class<T> clazz) throws IllegalArgumentException {
        for (EcSystem s : systems) {
            if (clazz.isInstance(s)) {
                return clazz.cast(s);
            }
        }

        throw new IllegalArgumentException("system not found "
                + clazz.getName());
    }

    public void dispose() throws IllegalStateException {
        if (updating) {
            throw new IllegalStateException("dispose not allowed during update");
        }

        // dispose entities
        for (Entity e : entities) {
            if (e.isActivated()) {
                e.deactivate();
                e.setRegistry(null);
            }
        }
        entities.clear();
        views.clear();

        // dispose systems
        for (int i = systems.size() - 1; i >= 0; --i) {
            EcSystem s = systems.get(i);
            s.setEnabled(false);
            s.removedFromRegistry(this);
            s.setRegistry(null);
        }
        systems.clear();
    }

    private interface Command {
        void execute();
    }
}