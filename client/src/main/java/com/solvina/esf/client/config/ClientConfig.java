package com.solvina.esf.client.config;

import com.solvina.esf.client.handler.ClientHandler;
import com.solvina.esf.netty.MessageRequestEncoder;
import com.solvina.esf.netty.MessageResponseDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:18 PM
 */
@Configuration
public class ClientConfig {
    private static Logger log = LogManager.getLogger(ClientConfig.class);

    public static void main(String... a){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ClientConfig.class);
        ctx.refresh();


        while(true){
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }


    @Value("${tcp.port:7878}")
    private int tcpPort;
    @Value("${tcp.port:localhost}")
    private String host;

    @Value("${worker.thread.count:5}")
    private int workerCount;

    @Value("${so.keepalive:true}")
    private boolean keepAlive;

    @Value("${so.backlog:100}")
    private int backlog;

    @Bean(name = "bootstrap")
    public Bootstrap bootstrap() throws InterruptedException {
        Bootstrap b = new Bootstrap();
        b.group(workerGroup())
                .channel(NioSocketChannel.class)
                .handler(channelInitializer());

        ChannelFuture f = b.connect(host, tcpPort).sync();

        f.channel().closeFuture().sync();
        return b;
    }

    @Bean
    public ChannelInitializer<SocketChannel> channelInitializer() {
        return new ChannelInitializer<SocketChannel>() {

            @Override
            public void initChannel(SocketChannel ch){
                ch.pipeline().addLast(new MessageRequestEncoder(),
                        new MessageResponseDecoder(), new ClientHandler());
            }
        };
    }

    @Bean(name = "tcpChannelOptions")
    public Map<ChannelOption<?>, Object> tcpChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, backlog);
        return options;
    }


    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }
}
