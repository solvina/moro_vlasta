package com.solvina.esf.server;

import com.solvina.esf.data.MessageRequest;
import com.solvina.esf.server.dao.MessageDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 11:20 AM
 */
@Component
public class EsfServerImpl implements EsfServer{
    private static Logger log = LogManager.getLogger(EsfServerImpl.class);

    private LocalDateTime latestPing = null;

    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private MessageConverter converter;

    @Override
    public void onPing(MessageRequest ping) {
        log.info("We just saw a ping message: {}",ping.toString());
        latestPing = ping.getCreated();
    }

    @Override
    public Optional<LocalDateTime> latestPingSeen() {
        return Optional.of(latestPing);
    }

    @Override
    public void onMessage(MessageRequest messageRequest) {
     log.info("We have received a message!");

     messageDAO.save(converter.toMessage(messageRequest));
    }
}
