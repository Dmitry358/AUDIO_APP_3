package com.example.audiochat;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  private final AudioHandler audioHandler;

  public WebSocketConfig(AudioHandler audioHandler) {
    this.audioHandler = audioHandler;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    // URL  для WebSocket: ws://localhost:8080/audio
    registry.addHandler(audioHandler, "/audio").setAllowedOrigins("*");
  }
}
