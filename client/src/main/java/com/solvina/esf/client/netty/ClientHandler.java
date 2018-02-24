package com.solvina.esf.client.netty;

import com.solvina.esf.data.MessageRequest;
import com.solvina.esf.data.MessageResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:59 PM
 */
@Component
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<MessageResponse> {
    private static Logger log = LogManager.getLogger(ClientHandler.class);

    ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx)

            throws Exception {
        log.info("Channel active {}", ctx.channel());

        this.ctx = ctx;

        send(welcomeMessage());
    }

    private MessageRequest welcomeMessage() {

        MessageRequest msg = new MessageRequest();
        msg.setCreated(LocalDateTime.now());
        msg.setText(
                "starting message on active channel");
        msg.setPing(false);
        return msg;

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel inactive {}", ctx.channel());
    }

    public void send(MessageRequest request) {
        if (ctx != null)
        {
            ChannelFuture cf = ctx.write(request);
            ctx.flush();
        } else
        {
            log.info("ctx is null");
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponse msg) throws Exception {
        log.info("Seen response: " + msg.getText());

    }

}
