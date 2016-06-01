package com.ocdsoft.bacta.swg.precu

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

/**
 * Created by kburkhardt on 12/9/14.
 */

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{ISO8601} %logger{4} [%-4level][%thread] %msg%n"
    }
}

logger("org.reflections",  WARN)
logger("io.netty",  WARN)
logger("com.couchbase",  WARN)
logger("com.ocdsoft.bacta.soe.connection.SoeUdpMessageBuilder", TRACE)
logger("com.ocdsoft.bacta.swg.server.game.object.template.server.ServerCreatureObjectTemplateTest", DEBUG)
logger("com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList", DEBUG)

root(INFO, ["STDOUT"])