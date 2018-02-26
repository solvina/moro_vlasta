package com.solvina.esf.server;

import com.solvina.esf.data.Message;
import com.solvina.esf.proto.MessageProtocol;
import com.solvina.esf.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 11:24 AM
 */
@Component
public class MessageConverter {
    private static Logger log = LogManager.getLogger(MessageConverter.class);

    public Message toMessage(MessageProtocol.MessageRequest request){
        Message ret = new Message();
        ret.setCreated(Utils.toLocalDateTime(request.getCreated()));
        ret.setMessage(request.getText());

        return ret;
    }

    public MessageProtocol.MessageRequest toRequest(Message message){
        MessageProtocol.MessageRequest request = MessageProtocol.MessageRequest.newBuilder()
        .setText(message.getMessage())
        .setCreated(Utils.toStamp(message.getCreated()))
        .setIsPing(false).build();

           return request;
    }

}
