package com.ocdsoft.bacta.swg.server.game;

import com.ocdsoft.bacta.engine.network.io.tcp.TcpServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kyle on 5/21/2016.
 */
public final class GameTcpServer extends TcpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(GameTcpServer.class);

    public GameTcpServer(int port) {
        super(new ChannelInitializer<SocketChannel>() { // (4)
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addFirst(new IdleStateHandler(0, 10, 0));
                ch.pipeline().addLast(new GameTcpServerHandler());
            }
        }, port);
    }

    private static class GameTcpServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(final ChannelHandlerContext ctx) {
            LOGGER.info("Channel Active");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            LOGGER.info("Channel Inactive");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
            // Discard the received data silently.
            ((ByteBuf) msg).release(); // (3)
            LOGGER.info("Someone sent me a message, why?");
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
                    LOGGER.info("Sent Heartbeat");
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
            ctx.close();
            LOGGER.error("Unexpected Error", cause);
        }
    }
}
