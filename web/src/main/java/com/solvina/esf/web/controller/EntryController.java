package com.solvina.esf.web.controller;

import com.solvina.esf.client.EsfClient;
import com.solvina.esf.data.Message;
import com.solvina.esf.proto.MessageProtocol;
import com.solvina.esf.server.MessageConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Collection;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 4:24 PM
 */
@Controller
public class EntryController {
    private static Logger log = LogManager.getLogger(EntryController.class);

    @Autowired
    private EsfClient esfClient;
    @Autowired
    private MessageConverter converter;

    @ModelAttribute("messages")
    Collection<MessageProtocol.MessageRequest> allMessages() {
        return esfClient.getRequests();
    }


    @ModelAttribute("message")
    Message getMessage() {
        return new Message();
    }

    @RequestMapping(path = "/")
    public String index(ModelMap model) {
        return "index";                                                   
    }

    @RequestMapping(path = "/addMessage", method = RequestMethod.POST)
    public String addMessage(@Valid @ModelAttribute("message") Message message,
                             BindingResult bindingResult, ModelMap model) {
        log.info("Received a message: " + message.toString() );
        if(bindingResult.hasErrors()){
            model.addAttribute("message",message);
            log.error("Incorrect message!");
            return "index";
        }

        esfClient.sendMessage(converter.toRequest(message));

        return "redirect:/";
    }

}
