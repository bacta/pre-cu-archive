package com.ocdsoft.bacta.swg.server.game.radialmenu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.ObjControllerBuilder;
import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueObjectMenuRequest;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.player.PlayerObjectService;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import com.ocdsoft.bacta.swg.shared.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableManager;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TObjectShortHashMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ocdsoft.bacta.swg.server.game.radialmenu.RadialMenuBuilder.RadialSubMenuBuilder;

/**
 * Created by crush on 5/30/2016.
 * <p>
 * Manages manipulating radial menus for objects.
 */
@Singleton
public final class ObjectMenuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMenuService.class);
    private static final String DATATABLE_PATH = "datatables/player/radial_menu.iff";
    private static final int COL_CAPTION = 0;
    private static final int COL_RANGE = 1;
    private static final int COL_COMMAND_NAME = 2;
    private static final int COL_USE_RADIAL_TARGET = 3;

    private final ServerObjectService serverObjectService;
    private final PlayerObjectService playerObjectService;
    private final TShortObjectMap<RadialMenuInfo> indexLookup;
    private final TObjectShortMap<String> nameLookup;

    @Inject
    public ObjectMenuService(final DataTableManager dataTableManager,
                             final ServerObjectService serverObjectService,
                             final PlayerObjectService playerObjectService) {
        this.indexLookup = new TShortObjectHashMap<>();
        this.nameLookup = new TObjectShortHashMap<>();
        this.serverObjectService = serverObjectService;
        this.playerObjectService = playerObjectService;

        final DataTable dataTable = dataTableManager.getTable(DATATABLE_PATH, true);
        loadDataTable(dataTable);
    }

    public void loadDataTable(final DataTable dataTable) {
        final int rowCount = dataTable.getNumRows();

        for (short i = 0; i < rowCount; ++i) {
            final String caption = dataTable.getStringValue(COL_CAPTION, i);

            LOGGER.debug("Loading radial menu info with caption {}", caption);

            final RadialMenuInfo info = new RadialMenuInfo(
                    dataTable.getFloatValue(COL_RANGE, i),
                    dataTable.getStringValue(COL_COMMAND_NAME, i),
                    dataTable.getIntValue(COL_USE_RADIAL_TARGET, i) != 0);

            indexLookup.put(i, info);
            nameLookup.put(caption, i);
        }
    }

    public short getMenuTypeByName(final String typeName) {
        if (nameLookup.containsKey(typeName))
            return nameLookup.get(typeName);

        return 0;
    }

    public void handleObjectMenuRequest(final SoeUdpConnection connection, final ServerObject actor, final MessageQueueObjectMenuRequest request) {
        final CreatureObject creatureObject = actor.asCreatureObject();
        final ServerObject targetObject = serverObjectService.get(request.getTargetId());

        if (targetObject == null)
            return;

        final boolean isGod = creatureObject.getConnection() != null && creatureObject.getConnection().isGod();

        final RadialMenuBuilder builder = RadialMenuBuilder.newBuilder();

        if (canPickUpObject(creatureObject, targetObject)) {
            builder.root(getMenuTypeByName("ITEM_PICKUP"), "", false);
        }

        if (!creatureObject.isInWorldCell() || isGod) {
            final RadialSubMenuBuilder moveBuilder = builder
                    .root(getMenuTypeByName("ITEM_MOVE"), "", false)
                    .item(getMenuTypeByName("ITEM_MOVE_FORWARD"), "", false)
                    .item(getMenuTypeByName("ITEM_MOVE_BACKWARD"), "", false)
                    .item(getMenuTypeByName("ITEM_MOVE_LEFT"), "", false)
                    .item(getMenuTypeByName("ITEM_MOVE_RIGHT"), "", false)
                    .item(getMenuTypeByName("ITEM_MOVE_UP"), "", false)
                    .item(getMenuTypeByName("ITEM_MOVE_DOWN"), "", false)
                    .item(getMenuTypeByName("ITEM_COPY_LOCATION"), "", false)
                    .item(getMenuTypeByName("ITEM_COPY_HEIGHT"), "", false);

            //TODO: Get the rotation degree from the creature.
            final RadialSubMenuBuilder rotateBuilder = builder
                    .root(getMenuTypeByName("ITEM_ROTATE"), "", false)
                    .item(getMenuTypeByName("ITEM_ROTATE_LEFT"), "@ui_radial:item_rotate_yaw +90", false)
                    .item(getMenuTypeByName("ITEM_ROTATE_RIGHT"), "@ui_radial:item_rotate_yaw -90", false)
                    .item(getMenuTypeByName("ITEM_ROTATE_RANDOM_YAW"), "", false);

            //final PlayerObject playerObject = playerObjectService.getPlayerObject(creatureObject);
            //NGE had some special collection that if earned, unlocked new rotation modes.
            //We will just enable them for now for Gods.
            if (isGod) {
                rotateBuilder
                        .item(getMenuTypeByName("ITEM_ROTATE_FORWARD"), "@ui_radial:item_rotate_pitch +90", false)
                        .item(getMenuTypeByName("ITEM_ROTATE_BACKWARD"), "@ui_radial:item_rotate_pitch -90", false)
                        .item(getMenuTypeByName("ITEM_ROTATE_RANDOM_PITCH"), "", false)
                        .item(getMenuTypeByName("ITEM_ROTATE_CLOCKWISE"), "@ui_radial:item_rotate_roll +90", false)
                        .item(getMenuTypeByName("ITEM_ROTATE_COUNTERCLOCKWISE"), "@ui_radial:item_rotate_pitch -90", false)
                        .item(getMenuTypeByName("ITEM_ROTATE_RANDOM_ROLL"), "", false)
                        .item(getMenuTypeByName("ITEM_ROTATE_RANDOM"), "", false);
            }

            rotateBuilder
                    .item(getMenuTypeByName("ITEM_ROTATE_RESET"), "", false)
                    .item(getMenuTypeByName("ITEM_ROTATE_COPY"), "", false);
        }

        //TODO: Append scripted object menu request for this object based on the accessing creature.

        //TODO: Append this message to the outgoing message queue.
        final MessageQueueObjectMenuRequest data = new MessageQueueObjectMenuRequest(
                targetObject.getNetworkId(), creatureObject.getNetworkId(), builder.build(), request.getSequenceId());

        final ObjControllerMessage msg = ObjControllerBuilder.newBuilder().send().reliable().authClient()
                .build(creatureObject.getNetworkId(), GameControllerMessageType.OBJECT_MENU_RESPONSE, data);

        connection.sendMessage(msg);
    }

    public boolean canPickUpObject(final CreatureObject actor, final ServerObject object) {
        //TODO: This logic likely belongs on another service. ServerObjectService and CreatureObjectService seem like good candidates.
        return true;
    }

    private CanManipulateResult objectMenuRequestCanManipulateObject(final CreatureObject player, final ServerObject item) {
        //TODO: Implement this.
        return new CanManipulateResult(false, true, true);
    }

    @AllArgsConstructor
    private static final class CanManipulateResult {
        public final boolean blockedByNoTrade;
        public final boolean showPickUpMenu;
        public final boolean canManipulate;
    }
}
