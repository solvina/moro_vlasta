package com.solvina.esf.netty;

import com.solvina.esf.data.MessageRequest;
import com.solvina.esf.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:36 PM
 */
public class MessageRequestEncoder extends MessageToByteEncoder<MessageRequest> {
    /**
     * Format is text length, text, date length, date, isPing
     * @param ctx
     * @param request
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageRequest request,
                          ByteBuf out) throws Exception {

        out.writeInt(request.getText().length());
        out.writeCharSequence(request.getText(),Utils.charset);

        String date = Utils.dmf.format(request.getCreated());
        System.out.println(date);
        out.writeInt(date.length());
        out.writeCharSequence(date,Utils.charset);

        out.writeBoolean(request.isPing());

    }
}
