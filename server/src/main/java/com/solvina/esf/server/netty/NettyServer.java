package com.solvina.esf.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 1:42 PM
 */
@Component
public class NettyServer {
    private static Logger log = LogManager.getLogger(NettyServer.class);

    @Autowired
    ServerBootstrap serverBootstrap;

    @Autowired
    @Qualifier("tcpServerAddress")
    private InetSocketAddress tcpPort;


    @PostConstruct
    public void startMe() throws InterruptedException {
        log.info("Start the server");
        // Make the connection attempt.
        ChannelFuture f = serverBootstrap.bind(tcpPort).sync();
//            clientHandler.setChannel(f.channel());
        // Wait until the connection is closed.
//        f.channel().closeFuture().sync();

    }
}
