package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.event.ObservableGameEvent;
import com.ocdsoft.bacta.swg.precu.message.player.LogoutMessage;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreaturePosture;
import com.ocdsoft.bacta.swg.precu.task.GameTask;
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
	public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {

        schedulerService.schedule(new LogoutTask(client), 30, TimeUnit.SECONDS);
	}

    private class LogoutTask extends GameTask {

        private final SoeUdpConnection connection;

        public LogoutTask(SoeUdpConnection connection) {
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
