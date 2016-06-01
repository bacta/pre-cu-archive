package com.ocdsoft.bacta.swg.server.actor;

import co.paralleluniverse.actors.Actor;
import co.paralleluniverse.actors.BasicActor;
import co.paralleluniverse.actors.MailboxConfig;
import co.paralleluniverse.common.util.Exceptions;
import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.FiberForkJoinScheduler;
import co.paralleluniverse.fibers.FiberScheduler;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.channels.Channels;
import org.junit.Test;

/**
 * Created by kyle on 5/19/2016.
 */
public class ActorTest {

    private FiberScheduler scheduler;
    static final MailboxConfig mailboxConfig = new MailboxConfig(10, Channels.OverflowPolicy.THROW);

    public ActorTest() {
        scheduler = new FiberForkJoinScheduler("test", 4, null, false);
    }

    @Test
    public void actorTest() {

//        Actor<Message, Integer> actor = spawnActor(new BasicActor<Message, Integer>(mailboxConfig) {
//            @Override
//            protected Integer doRun() throws SuspendExecution, InterruptedException {
//                throw new RuntimeException("foo");
//            }
//        });

    }

    private <Message, V> Actor<Message, V> spawnActor(Actor<Message, V> actor) {
        Fiber fiber = new Fiber("actor", scheduler, actor);
        fiber.setUncaughtExceptionHandler(new Strand.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Strand s, Throwable e) {
                e.printStackTrace();
                throw Exceptions.rethrow(e);
            }
        });
        fiber.start();
        return actor;
    }

    static class Message {
        final int num;

        public Message(int num) {
            this.num = num;
        }
    }
}
