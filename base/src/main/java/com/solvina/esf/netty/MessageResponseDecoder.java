package com.solvina.esf.netty;

import com.solvina.esf.data.MessageResponse;
import com.solvina.esf.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:34 PM
 */
public class MessageResponseDecoder extends ReplayingDecoder<MessageResponse> {

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf byteBuf, List<Object> out) throws Exception {

        MessageResponse request = new MessageResponse();

        int textLength = byteBuf.readInt();
        String text = byteBuf.readCharSequence(textLength, Utils.charset).toString();
        request.setText(text);

        out.add(request);

    }
}
