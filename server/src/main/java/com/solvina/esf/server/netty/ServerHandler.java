package com.solvina.esf.server.netty;

import com.solvina.esf.proto.MessageProtocol;
import com.solvina.esf.server.EsfServer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:24 PM
 */
@Component
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<MessageProtocol.MessageRequest> {
    private static Logger log = LogManager.getLogger(ServerHandler.class);

    @Autowired
    @Qualifier("esfServer")
    private EsfServer esfServer;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel active {}",ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel inactive {}",ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol.MessageRequest msg) throws Exception {
        log.info("We need process the message! {}", msg);
        if(esfServer == null){
           log.error("Server is null!");
           return;
       }

        MessageProtocol.MessageResponse response = MessageProtocol.MessageResponse
                .newBuilder().setText("Received: " + msg.getText()).build();
        ctx.write(response);

        if (msg.getIsPing())
            esfServer.onPing(msg);
        else
            esfServer.onMessage(msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
