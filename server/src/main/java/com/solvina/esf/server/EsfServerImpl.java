package com.solvina.esf.server;

import com.solvina.esf.proto.MessageProtocol;
import com.solvina.esf.server.dao.MessageDAO;
import com.solvina.esf.data.Message;
import com.solvina.esf.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 11:20 AM
 */
@Component
@Qualifier("esfServer")
public class EsfServerImpl implements EsfServer{
    private static Logger log = LogManager.getLogger(EsfServerImpl.class);

    private LocalDateTime latestPing = null;

    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private MessageConverter converter;

    @Override
    public void onPing(MessageProtocol.MessageRequest ping) {
        log.info("We just saw a ping message: {}",ping.toString());
        latestPing = Utils.toLocalDateTime(ping.getCreated());
    }

    @Override
    public Optional<LocalDateTime> latestPingSeen() {
        return Optional.of(latestPing);
    }

    @Override
    public void onMessage(MessageProtocol.MessageRequest messageRequest) {
     log.info("We have received a message!");

     Message toStore = converter.toMessage(messageRequest);
     log.info("to store: {}",toStore);
     Message ret = messageDAO.save(toStore);
     log.info(ret);
    }
}
