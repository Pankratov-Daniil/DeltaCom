package com.deltacom.app.controllers;

import com.deltacom.app.entities.Option;
import com.deltacom.app.entities.Tariff;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for processing websocket connections
 */
@Controller
public class SubscribersController {
    @MessageMapping("/subscrToOptions")
    @SendTo("/topic/options")
    public List<Option> subscrToOptions() {
        return new ArrayList<>();
    }

    @MessageMapping("/subscrToTariffs")
    @SendTo("/topic/tariffs")
    public List<Tariff> subscrToTariffs() {
        return new ArrayList<>();
    }
}
