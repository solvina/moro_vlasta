package com.solvina.esf.client;

import com.solvina.esf.data.MessageRequest;

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
    void SendMessage(MessageRequest messageRequest);

    /**
     * Gets a collection of already send messages
     * @return
     */
    Collection<MessageRequest> getRequests();
}
