package dev.ajitsingh.hellowebsockets.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new QuestionHandler(), "/metrics");
    }

    static class QuestionHandler extends TextWebSocketHandler {

        private List<WebSocketSession> webSocketSessions = new CopyOnWriteArrayList<>();


        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            webSocketSessions.add(session);

        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            super.handleMessage(session, message);
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            for (WebSocketSession we :
                    webSocketSessions) {
                try {
                    we.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
            super.handlePongMessage(session, message);
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            super.handleTransportError(session, exception);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            super.afterConnectionClosed(session, status);
        }

        @Override
        public boolean supportsPartialMessages() {
            return super.supportsPartialMessages();
        }
    }
}
