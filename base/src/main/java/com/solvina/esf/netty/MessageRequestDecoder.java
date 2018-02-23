package com.solvina.esf.netty;

import com.solvina.esf.data.MessageRequest;
import com.solvina.esf.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.time.LocalDateTime;
import java.util.List;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:34 PM
 */
public class MessageRequestDecoder extends ReplayingDecoder<MessageRequest> {

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf byteBuf, List<Object> out) throws Exception {

        MessageRequest request = new MessageRequest();

        int textLength = byteBuf.readInt();
        String text = byteBuf.readCharSequence(textLength, Utils.charset).toString();

        int dateLength = byteBuf.readInt();
        String dateStr = byteBuf.readCharSequence(dateLength, Utils.charset).toString();

        boolean isPing = byteBuf.readBoolean();

        request.setPing(isPing);
        request.setText(text);
        request.setCreated(LocalDateTime.parse(dateStr,Utils.dmf));

        out.add(request);

    }
}
