package com.ocdsoft.bacta.swg.precu.task;

import com.ocdsoft.bacta.swg.server.game.event.ObservableGameEvent;
import com.ocdsoft.network.service.scheduler.Task;

/**
 * Created by Kyle on 9/5/2014.
 */
public abstract class GameTask extends Task<ObservableGameEvent> {
    @Override
    public void update(ObservableGameEvent observableGameEvent) {

    }
}
