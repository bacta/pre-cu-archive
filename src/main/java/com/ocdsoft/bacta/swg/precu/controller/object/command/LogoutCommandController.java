package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.annotations.Command;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.event.ObservableGameEvent;
import com.ocdsoft.bacta.swg.server.game.message.player.LogoutMessage;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreaturePosture;
import com.ocdsoft.bacta.swg.server.game.task.GameTask;
import com.ocdsoft.network.service.scheduler.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@Command(id=0x3b65950)
public class LogoutCommandController implements CommandController {

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final SchedulerService schedulerService;

    @Inject
    public LogoutCommandController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

	@Override
	public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {

        schedulerService.schedule(new LogoutTask(client), 30, TimeUnit.SECONDS);
	}

    private class LogoutTask extends GameTask {

        private final GameClient client;

        public LogoutTask(GameClient client) {
            this.client = client;
            client.getCharacter().setPosture(CreaturePosture.SITTING);
            client.getCharacter().register(this, ObservableGameEvent.POSTURE_CHANGE);
        }

        @Override
        public void run() {
            if(client != null) {
                client.getCharacter().unregister(this, ObservableGameEvent.POSTURE_CHANGE);

                LogoutMessage logout = new LogoutMessage();
                client.sendMessage(logout);

                client.disconnect();
                client.close();
            }
        }

        @Override
        public void update(ObservableGameEvent observableGameEvent) {
            if(observableGameEvent == ObservableGameEvent.POSTURE_CHANGE) {
                client.getCharacter().unregister(this, ObservableGameEvent.POSTURE_CHANGE);
                cancel(false);
            }
        }
    }
}
