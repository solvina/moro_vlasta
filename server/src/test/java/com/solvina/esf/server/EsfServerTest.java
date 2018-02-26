package com.solvina.esf.server;

import com.solvina.esf.data.Message;
import com.solvina.esf.proto.MessageProtocol;
import com.solvina.esf.server.config.ServerConfig;
import com.solvina.esf.server.dao.MessageDAO;
import com.solvina.esf.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 10:41 AM
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServerConfig.class})
class EsfServerTest {

    @Autowired
    EsfServer server;

    @Autowired
    MessageDAO messageDAO;

    private static final LocalDateTime testStamp = LocalDateTime.of(2018, 02, 23, 15, 00);

    @Test
    void testPing() {
        server.onPing(createMessageRequest("Ping message"));

        assertEquals(server.latestPingSeen().get(), testStamp, "Stamps must match");
    }

    @Test
    void onMessage() {
        String testMsg = "My very own test message";
        messageDAO.deleteAll();
        server.onMessage(createMessageRequest(testMsg));

        List<Message> messages = messageDAO.findAll();

        assertEquals(1, messages.size(), "We expect exactly one message here");
        assertEquals(testMsg, messages.get(0).getMessage(), "Messages must match");
    }


    private MessageProtocol.MessageRequest createMessageRequest(String str) {
        MessageProtocol.MessageRequest msg = MessageProtocol.MessageRequest.newBuilder()
                .setCreated(Utils.toStamp(testStamp))
                .setText(str).build();

        return msg;
    }
}