package com.ocdsoft.bacta.swg.server.login;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Observable;

/**
 * Created by kyle on 5/21/2016.
 */
public final class GameTcpClient extends Observable {

    private final static Logger LOGGER = LoggerFactory.getLogger(GameTcpClient.class);

    private final InetSocketAddress remoteAddress;
    private final ChannelInboundHandlerAdapter handler;

    /**
     * This constructor uses the basic handler that just keeps connected indefinitely
     * Uses the default ChannelInitializer
     * @param remoteAddress host to connect to
     */
    public GameTcpClient(final InetSocketAddress remoteAddress) {
        this(remoteAddress, new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addFirst(new ReadTimeoutHandler(15));
                ch.pipeline().addLast(new GameServerHandler());
            }
        });
    }

    /**
     * This constructor is for when you want to provide a handler to expand the basic functionality
     * @param remoteAddress host to connect to
     * @param handler provided handler
     */
    public GameTcpClient(final InetSocketAddress remoteAddress, final ChannelInboundHandlerAdapter handler) {
        this.remoteAddress = remoteAddress;
        this.handler = handler;
    }

    public void start()  {

        LOGGER.info("Starting TCP Connection to {}", remoteAddress);

        final Thread tcpThread = new Thread(new Runnable() {

            @Override
            public void run() {
                EventLoopGroup workerGroup = new NioEventLoopGroup();

                try {
                    Bootstrap b = new Bootstrap(); // (1)
                    b.group(workerGroup); // (2)
                    b.channel(NioSocketChannel.class); // (3)
                    b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
                    b.handler(handler);

                    // Start the client.
                    ChannelFuture f = b.connect(remoteAddress); // (5)

                    // Wait until the connection is closed.
                    f.channel().closeFuture().sync();

                } catch (InterruptedException e) {
                    LOGGER.error("Interrupted", e);
                } finally {
                    workerGroup.shutdownGracefully();
                }

                notifyObservers(remoteAddress);
            }
        });
        tcpThread.start();
    }

    private static class GameServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            LOGGER.trace("Heartbeat received");
        }

        @Override
        public void channelActive(final ChannelHandlerContext ctx) {
            LOGGER.trace("Channel Active");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            LOGGER.trace("Channel Inactive");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
            LOGGER.error("Disconnected from GameServer", cause);
        }
    }
}
