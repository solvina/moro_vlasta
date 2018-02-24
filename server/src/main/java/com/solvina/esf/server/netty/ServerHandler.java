package com.solvina.esf.server.netty;

import com.solvina.esf.data.MessageRequest;
import com.solvina.esf.data.MessageResponse;
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
public class ServerHandler extends SimpleChannelInboundHandler<MessageRequest> {
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
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest msg) throws Exception {
        MessageResponse response = new MessageResponse();
        response.setText("Received: " + msg.getText());
        ctx.write(response);

        log.info("We need process the message! {}", msg);
        if (msg.isPing())
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
