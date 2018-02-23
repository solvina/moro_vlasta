package com.solvina.esf.server;

import com.solvina.esf.data.MessageRequest;
import com.solvina.esf.server.model.Message;
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

    public Message toMessage(MessageRequest request){
        Message ret = new Message();
//        ret.setCreated(request.getCreated());
        ret.setMessage(request.getText());

        return ret;
    }

}
