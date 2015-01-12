package com.ocdsoft.bacta.swg.precu.task;

import com.ocdsoft.bacta.engine.service.scheduler.Task;
import com.ocdsoft.bacta.swg.precu.event.ObservableGameEvent;

/**
 * Created by Kyle on 9/5/2014.
 */
public abstract class GameTask extends Task<ObservableGameEvent> {
    @Override
    public void update(ObservableGameEvent observableGameEvent) {

    }
}
