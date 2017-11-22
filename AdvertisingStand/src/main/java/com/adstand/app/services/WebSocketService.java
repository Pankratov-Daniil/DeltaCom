package com.adstand.app.services;

import com.deltacom.dto.TariffDTOwOpts;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for websocket connections
 */
@ServerEndpoint(value = "/tariffSubscription")
@SessionScoped
public class WebSocketService {
    private static final Logger logger = LogManager.getLogger(WebSocketService.class);
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<>());

    /**
     * Function calls when new client connected to socket
     */
    @OnOpen
    public void onOpen(Session peer) {
        peers.add(peer);
    }

    /**
     * Function calls when client disconnected
     */
    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

    /**
     * Function that send new tariffs to all clients
     * @param tariffs new list of tariffs
     */
    public static void sendMessage(List<TariffDTOwOpts> tariffs) {
        for(Session peer : peers) {
            try {
                peer.getBasicRemote().sendText(new ObjectMapper().writeValueAsString(tariffs));
            } catch (IOException e) {
                logger.error("Can't send message to stand: " + e);
            }
        }
    }
}
