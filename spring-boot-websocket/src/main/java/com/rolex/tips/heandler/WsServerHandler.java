/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.heandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author rolex
 * @since 2021
 */
@Component
@Slf4j
@ServerEndpoint("/ws")
public class WsServerHandler {

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
//        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
//
        String id = session.getId();
        log.info("WebSocket连接建立, id={}", id);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到消息: {}", message);
        session.getBasicRemote().sendText("hello, " + message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        log.info("WebSocket连接断开");
        session.close();
    }

    @OnError
    public void onError(Session sess, Throwable e) {
        Throwable cause = e.getCause();
        if (cause != null) {
            System.out.println("Error-info: cause->" + cause);
        }
        try {
            // Likely EOF (i.e. user killed session)
            // so just Close the input stream as instructed
            sess.close();
        } catch (IOException ex) {
            System.out.println("Handling eof, A cascading IOException was caught: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            System.out.println("Session error handled. (likely unexpected EOF) resulting in closing User Session.");

        }
    }
}
