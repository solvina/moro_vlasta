package com.solvina.esf.server;

import com.solvina.esf.proto.MessageProtocol;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 9:58 AM
 */
public interface EsfServer {

    /**
     * at the moment it only logs the ping
     * @param ping
     */
    void onPing(MessageProtocol.MessageRequest ping);

    /**
     * When we saw the last ping
     * @return
     */
    Optional<LocalDateTime> latestPingSeen();

    /**
     * Stores the message request to db
     * @param messageRequest
     */
    void onMessage(MessageProtocol.MessageRequest messageRequest);
}
