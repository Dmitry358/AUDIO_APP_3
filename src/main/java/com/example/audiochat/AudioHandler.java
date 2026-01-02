package com.example.audiochat;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class AudioHandler extends BinaryWebSocketHandler {

  private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    sessions.add(session);
    System.out.println("Клиент подключился: " + session.getId());
  }

  @Override
  protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
    // Рассылаем аудио всем подключённым клиентам
    synchronized (sessions) {
      for (WebSocketSession s : sessions) {
        if (s.isOpen()) {
          s.sendMessage(message);
        }
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    sessions.remove(session);
    System.out.println("Клиент отключился: " + session.getId());
  }
}
