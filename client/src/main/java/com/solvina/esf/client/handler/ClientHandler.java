package com.solvina.esf.client.handler;

import com.solvina.esf.data.MessageRequest;
import com.solvina.esf.data.MessageResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:59 PM
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static Logger log = LogManager.getLogger(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx)
      throws Exception {

        MessageRequest msg = new MessageRequest();
        msg.setCreated(LocalDateTime.now());
        msg.setText(
          "starting message on active channel");
        ChannelFuture future = ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        log.info("Seen response: " + ((MessageResponse) msg).getText());
        ctx.close();
    }
}
