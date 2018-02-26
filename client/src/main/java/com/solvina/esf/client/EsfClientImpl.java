package com.solvina.esf.client;

import com.solvina.esf.client.netty.ClientHandler;
import com.solvina.esf.proto.MessageProtocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:07 PM
 */
@Component
public class EsfClientImpl implements EsfClient {
    private static Logger log = LogManager.getLogger(EsfClientImpl.class);

    private final List<MessageProtocol.MessageRequest> messagesSent = new CopyOnWriteArrayList<>();

    @Autowired
    private ClientHandler clientHandler;

    @Override
    @Scheduled(fixedDelay = 30000, initialDelay = 1000)
    public void ping() {
        log.info("Sending ping");
        send(createPing());
    }

    @Override
    public void sendMessage(MessageProtocol.MessageRequest messageRequest) {
        send(messageRequest);
        messagesSent.add(messageRequest);
    }

    @Override
    public Collection<MessageProtocol.MessageRequest> getRequests() {
        return new ArrayList<>(messagesSent);
    }

    private void send(MessageProtocol.MessageRequest messageRequest) {
        clientHandler.send(messageRequest);
    }

    private MessageProtocol.MessageRequest createPing() {
        MessageProtocol.MessageRequest ret = MessageProtocol.MessageRequest.newBuilder()
                .setText("Ping! I am alive!")
                .setIsPing(true).build();
        return ret;
    }

}
