package com.ocdsoft.bacta.swg.server.game.object.sui;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Vector3f;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public final class SuiPageData implements ByteBufferWritable {

    private static final Logger logger = LoggerFactory.getLogger(SuiPageData.class);
    private static final AtomicInteger lastId = new AtomicInteger();

    private final int pageId;
    private final String pageName;
    private final long associatedObjectId;
    private final Vector3f associatedLocation;
    private final float maxRangeFromObject;

    protected final List<SuiCommand> commands = new ArrayList<SuiCommand>();
    protected final TByteObjectMap<SuiCommand> callbacks = new TByteObjectHashMap<SuiCommand>();

    public SuiPageData(String rootPage) {
        this(rootPage, 0, 0);
    }

    public SuiPageData(String rootPage, long targetNetworkId, float forceCloseDistance) {
        this.pageId = lastId.incrementAndGet();

        this.pageName = rootPage;
        this.associatedObjectId = targetNetworkId;
        this.associatedLocation = new Vector3f(0f, 0f, 0f);
        this.maxRangeFromObject = forceCloseDistance;
    }

    //TODO: Implement
//    public SuiPageData(ByteBuffer buffer) {
//
//    }

    public void setProperty(String widget, String property, String value) {
        SuiCommand command = new SuiCommand(SuiCommand.SCT_setProperty);
        command.addWideParameter(value);
        command.addNarrowParameter(widget);
        command.addNarrowParameter(property);

        addCommand(command);
    }

    public void addDataItem(String widget, String property, String value) {
        SuiCommand command = new SuiCommand(SuiCommand.SCT_addDataItem);
        command.addWideParameter(value);
        command.addNarrowParameter(widget);
        command.addNarrowParameter(property);

        addCommand(command);
    }

    public void clearDataSource(String dataSource) {
        SuiCommand command = new SuiCommand(SuiCommand.SCT_clearDataSource);
        command.addNarrowParameter(dataSource);

        addCommand(command);
    }

    public void addDataSourceContainer(String parent, String name) {
        SuiCommand command = new SuiCommand(SuiCommand.SCT_addDataSourceContainer);
        command.addNarrowParameter(parent);
        command.addNarrowParameter(name);

        addCommand(command);
    }

    public void clearDataSourceContainer(String dataSourceContainer) {
        SuiCommand command = new SuiCommand(SuiCommand.SCT_clearDataSourceContainer);
        command.addNarrowParameter(dataSourceContainer);

        addCommand(command);
    }

    public void addChildWidget(String parent, String type, String name) {
        SuiCommand command = new SuiCommand(SuiCommand.SCT_addChildWidget);
        command.addNarrowParameter(parent);
        command.addNarrowParameter(type);
        command.addNarrowParameter(name);

        addCommand(command);
    }

    public void subscribeToEvent(byte eventType, String parent, String callback) {
        if (callbacks.containsKey(eventType)) {
            logger.warn(
                    String.format("SuiPageData::addCommand attempt to add duplicate SCT_subscribeToEvent command.  Type=[%d], target=[%s]",
                            eventType, callback));

            return;
        }

        SuiCommand command = new SuiCommand(SuiCommand.SCT_subscribeToEvent);
        command.addNarrowParameter(parent);
        command.addNarrowParameter(String.valueOf((char) eventType));
        command.addNarrowParameter(callback);

        addCommand(command);
        callbacks.put(eventType, command);
    }

    public void subscribeToPropertyForEvent(byte eventType, String widget, String property) {
        SuiCommand command = callbacks.get(eventType);

        if (command == null) {
            logger.warn("Attempted to add properties for event without subscribing a callback first.");
            return;
        }

        command.addNarrowParameter(widget);
        command.addNarrowParameter(property);
    }

    private void addCommand(SuiCommand command) {
        commands.add(command);
    }


    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(pageId);
        BufferUtil.putAscii(buffer, pageName);
        buffer.putInt(commands.size());

        for (int i = 0; i < commands.size(); i++) {
            commands.get(i).writeToBuffer(buffer);
        }

        buffer.putLong(associatedObjectId);
        buffer.putFloat(maxRangeFromObject);
        buffer.putLong(0);
    }
}
