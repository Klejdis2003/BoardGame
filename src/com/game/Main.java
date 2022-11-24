package com.game;

import com.game.management.GameSession;

public class Main {
    public static final GameSession gameSession = new GameSession();
    public static void main(String[] args) {
       gameSession.begin();
    }
}
