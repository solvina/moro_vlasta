package com.solvina.esf.netty;

import com.solvina.esf.data.MessageResponse;
import com.solvina.esf.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:36 PM
 */
public class MessageResponseEncoder extends MessageToByteEncoder<MessageResponse> {
    /**
     * Format is text length, text
     * @param ctx
     * @param request
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageResponse request,
                          ByteBuf out) throws Exception {
        out.writeInt(request.getText().length());
        out.writeCharSequence(request.getText(),Utils.charset);
    }
}
