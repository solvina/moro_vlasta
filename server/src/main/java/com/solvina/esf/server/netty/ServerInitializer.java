package com.solvina.esf.server.netty;

import com.solvina.esf.proto.MessageProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * User: Vlastimil
 * Date: 2/26/18
 * Time: 10:27 AM
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private static Logger log = LogManager.getLogger(ServerInitializer.class);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new ProtobufVarint32FrameDecoder());
        p.addLast(new ProtobufDecoder(MessageProtocol.MessageRequest.getDefaultInstance()));

        p.addLast(new ProtobufVarint32LengthFieldPrepender());
        p.addLast(new ProtobufEncoder());

        p.addLast(new ServerHandler());

    }
}
