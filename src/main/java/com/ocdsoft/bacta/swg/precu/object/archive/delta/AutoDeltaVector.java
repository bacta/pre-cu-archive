package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.lang.NotImplementedException;
import com.ocdsoft.bacta.swg.util.ByteAppender;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.*;

public class AutoDeltaVector<T> extends AutoDeltaContainer implements Iterable<T> {
    private transient final List<Command<T>> commands = new ArrayList<>(5);
    private final List<T> v;
    private transient int baselineCommandCount = 0;

    public AutoDeltaVector(AutoDeltaByteStream owner) {
        super(owner);

        this.v = new ArrayList<>();
    }

    public AutoDeltaVector(int capacity, AutoDeltaByteStream owner) {
        super(owner);

        this.v = new ArrayList<>(capacity);
    }

    public AutoDeltaVector(List<T> initialList, AutoDeltaByteStream owner) {
        super(owner);

        this.v = new ArrayList(initialList);
    }

    @Override
    public void clearDelta() { commands.clear(); }

    public T get(int index) {
        return v.get(index);
    }

    public void insert(int index, T object) {
        v.add(index, object);
        commands.add(new Command(Command.Insert, index, object));
        ++baselineCommandCount;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    public void set(int index, T object) {
        v.set(index, object);
        commands.add(new Command(Command.Set, index, object));
        ++baselineCommandCount;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    public void set(Collection<T> collection) {
        v.clear();
        v.addAll(collection);
        commands.add(new Command(Command.SetAll, collection.size(), collection));
        ++baselineCommandCount;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    public void clear() {
        v.clear();
        commands.add(new Command(Command.Clear, 0, null));
        ++baselineCommandCount;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    public void erase(int index) {
        v.remove(index);
        commands.add(new Command(Command.Erase, index, null));
        ++baselineCommandCount;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    public void remove() {
        this.erase(v.size() - 1);
    }

    public void add(T object) {
        this.insert(v.size(), object);
    }

    public int size() {
        return v.size();
    }

    public int find(T object) { return v.indexOf(object); }

    public boolean contains(T object) { return v.contains(object); }

    public boolean isDirty() {
        return commands.size() > 0;
    }

    @Override
    public void packDelta(ByteBuffer buffer) {
        try {
            buffer.putInt(commands.size());
            buffer.putInt(baselineCommandCount);

            for (Command command : commands) {
                buffer.put(command.cmd);

                switch (command.cmd) {
                    case Command.Erase:
                        buffer.putShort(command.index);
                        break;
                    case Command.Insert:
                        buffer.putShort(command.index);
                        ByteAppender.append((T) command.value, buffer);
                        break;
                    case Command.Set:
                        buffer.putShort(command.index);
                        ByteAppender.append((T) command.value, buffer);
                        break;
                    case Command.SetAll:
                        //This isn't technically correct, but it will do.
                        Collection<T> collection = (Collection<T>) command.value;

                        buffer.putShort(command.index);
                        for (T obj : collection)
                            ByteAppender.append(obj, buffer);
                    default:
                        continue;
                }
            }

            clearDelta();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableList(v).iterator();
    }

    @Override
    public void pack(ByteBuffer buffer) {
        try {
            buffer.putInt(v.size());
            buffer.putInt(baselineCommandCount);

            for (T obj : v)
                ByteAppender.append(obj, buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unpack(ByteBuffer buffer) {
        throw new NotImplementedException();
    }

    private final class Command<T> {
        private static final int Erase = 0x00;
        private static final int Insert = 0x01;
        private static final int Set = 0x02;
        private static final int SetAll = 0x03;
        private static final int Clear = 0x04;

        @Getter private final byte cmd;
        @Getter private final short index;
        @Getter private final Object value;

        public Command(int cmd, int index, T value) {
            this.cmd = (byte) cmd;
            this.index = (short) index;
            this.value = value;
        }
    }
}
