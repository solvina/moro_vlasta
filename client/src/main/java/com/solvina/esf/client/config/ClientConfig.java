package com.solvina.esf.client.config;

import com.solvina.esf.client.netty.ClientHandler;
import com.solvina.esf.client.netty.ClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:18 PM
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.solvina.esf")

public class ClientConfig {
    private static Logger log = LogManager.getLogger(ClientConfig.class);

    public static void main(String... a) throws InterruptedException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ClientConfig.class);
        ctx.refresh();

//        ctx.getBean(NettyClient.class).startMe();

    }


    @Value("${tcp.port:7878}")
    @Qualifier("clientPort")
    private int tcpPort;
    @Value("${tcp.port:localhost}")
    private String host;

    @Value("${worker.thread.count:5}")
    private int workerCount;

    @Value("${so.keepalive:true}")
    private boolean keepAlive;

    @Value("${so.backlog:100}")
    private int backlog;


    @Bean(name = "clientAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(host, tcpPort);
    }

    @Autowired
    private ClientHandler clientHandler;

    @Bean(name = "bootstrapClient")
    public Bootstrap bootstrap() throws InterruptedException {

        EventLoopGroup group = bossGroup();
        log.info("Creating client");


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(clientInitializer());


        log.info("Created client");

        return bootstrap;

    }

    @Bean(name="clientSllCTX")

    public SslContext sslCtx() throws SSLException {
        return  SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
    }


    @Bean
    ClientInitializer clientInitializer(){
        return new ClientInitializer();
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup();
    }
}
