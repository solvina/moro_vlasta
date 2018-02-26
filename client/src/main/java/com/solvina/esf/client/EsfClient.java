package com.solvina.esf.client;

import com.solvina.esf.proto.MessageProtocol;

import java.util.Collection;

/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 9:55 AM
 */
public interface EsfClient {

    /**
     * Pings the server with a alive notification
     */
    void ping();

    /**
     * Sends a message using netty to server
     * @param messageRequest
     */
    void sendMessage(MessageProtocol.MessageRequest messageRequest);

    /**
     * Gets a collection of already send messages
     * @return
     */
    Collection<MessageProtocol.MessageRequest> getRequests();
}
