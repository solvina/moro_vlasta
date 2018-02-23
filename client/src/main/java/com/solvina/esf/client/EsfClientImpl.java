package com.solvina.esf.client;

import com.solvina.esf.data.MessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final List<MessageRequest> messagesSent = new CopyOnWriteArrayList<>();

    @Override
    public void ping() {
        send(createPing());
    }

    @Override
    public void sendMessage(MessageRequest messageRequest) {
       send(messageRequest);
       messagesSent.add(messageRequest);
    }

    @Override
    public Collection<MessageRequest> getRequests() {
        return new ArrayList<>(messagesSent);
    }

    private void send(MessageRequest messageRequest){
        //todo send using netty
    }

    private MessageRequest createPing(){
        MessageRequest ret = new MessageRequest();
        ret.setText("Ping! I am alive!");
        ret.setPing(true);
        return ret;
    }

}
