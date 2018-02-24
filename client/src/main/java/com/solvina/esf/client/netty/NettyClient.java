package com.solvina.esf.client.netty;

import io.netty.bootstrap.Bootstrap;
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
 * Time: 9:29 PM
 */
@Component
public class NettyClient {
    private static Logger log = LogManager.getLogger(NettyClient.class);

    //    @Autowired
//    private MessageRequestEncoder messageRequestEncoder;
//    @Autowired
//    private MessageResponseDecoder messageResponseDecoder;
    @Autowired
    @Qualifier("clientAddress")
    private InetSocketAddress clientAddress;

    @Autowired
    @Qualifier("bootstrapClient")
    private Bootstrap bootstrap;

    @PostConstruct
    public void startMe() throws InterruptedException {
        log.info("Start the client");
        // Make the connection attempt.
        ChannelFuture f = bootstrap.connect(clientAddress).sync();
//            clientHandler.setChannel(f.channel());
        // Wait until the connection is closed.
//        f.channel().closeFuture().sync();

    }
}
