package com.ocdsoft.bacta.swg.server.game;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kyle on 5/27/2016.
 */
public class GameTcpServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameTcpServerHandler.class);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        LOGGER.trace("Channel Active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.trace("Channel Inactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.
        ((ByteBuf) msg).release(); // (3)
        LOGGER.trace("Someone sent me a message, why?");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                //ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                final ByteBuf time = ctx.alloc().buffer(4); // (2)
                time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
                ctx.writeAndFlush(time);
                LOGGER.trace("Sent Heartbeat");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        ctx.close();
        LOGGER.error("Unexpected Error", cause);
    }
}
