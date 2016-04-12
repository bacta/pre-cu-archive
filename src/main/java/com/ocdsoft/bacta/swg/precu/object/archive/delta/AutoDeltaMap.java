package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.util.ByteAppender;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.Map.Entry;

public class AutoDeltaMap<K, V> extends AutoDeltaContainer implements Iterable<Entry<K, V>> {
    private transient final List<Command> commands = new ArrayList<Command>(5);
    private final Map<K, V> map;
    private transient int baselineCommandCount = 0;

    public AutoDeltaMap(AutoDeltaByteStream owner) {
        super(owner);

        map = new HashMap<K, V>();
    }

    @Override
    public void clearDelta() {
        commands.clear();
    }

    public V find(K key) {
        return map.get(key);
    }

    public int size() {
        return map.size();
    }

    public void insert(K key, V value) {
        map.put(key, value);
        commands.add(new Command(Command.Insert, key, value));
        ++baselineCommandCount;

        if (owner != null)
            owner.addToDirtyList(this);

    }

    public void set(K key, V value) {
        map.put(key, value);
        commands.add(new Command(Command.Set, key, value));
        ++baselineCommandCount;

        if (owner != null)
            owner.addToDirtyList(this);

    }

    public void erase(K key) {
        V value = map.remove(key);
        commands.add(new Command(Command.Erase, key, value));
        ++baselineCommandCount;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    public boolean empty() {
        return map.size() == 0;
    }

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
                ByteAppender.append(command.key, buffer);
                ByteAppender.append(command.value, buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {

    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return map.entrySet().iterator();
    }

    @Override
    public void pack(ByteBuffer buffer) {
        try {
            buffer.putInt(map.size());
            buffer.putInt(baselineCommandCount);

            for (Entry<K, V> entry : map.entrySet()) {
                ByteAppender.append(entry.getKey(), buffer);
                ByteAppender.append(entry.getValue(), buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unpack(ByteBuffer buffer) {

    }

    private final class Command {
        private static final int Erase = 0x0;
        private static final int Insert = 0x1;
        private static final int Set = 0x2;

        @Getter private final byte cmd;
        @Getter private final K key;
        @Getter private final V value;

        public Command(int cmd, K key, V value) {
            this.cmd = (byte) cmd;
            this.key = key;
            this.value = value;
        }
    }
}
