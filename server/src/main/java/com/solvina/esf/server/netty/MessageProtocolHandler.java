package com.solvina.esf.server.netty;

import com.solvina.esf.data.MessageRequest;
import com.solvina.esf.data.MessageResponse;
import com.solvina.esf.server.EsfServer;
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
public class MessageProtocolHandler extends SimpleChannelInboundHandler<MessageRequest> {
    private static Logger log = LogManager.getLogger(MessageProtocolHandler.class);

    @Autowired
    @Qualifier("esfServer")
    private EsfServer esfServer;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest msg) throws Exception {
        MessageResponse response = new MessageResponse();
        response.setText("Received: " + msg.getText());
        ctx.write(response);

        log.info("We need process the message!" + msg);
        if (msg.isPing())
        {
            esfServer.onPing(msg);
        } else
        {
            esfServer.onMessage(msg);
        }
    }
}
