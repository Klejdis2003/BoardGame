package com.game.management;

import java.util.Map;

public abstract class PlayerIds {
    public static final Map<Integer, String> MAPPING =
            Map.of(1, "Player", 2, "Computer", 0, "Draw");
    public static final Map<Integer, String> GAME_END_MESSAGES =
            Map.of(
                    0, "It is a draw, better luck next time", //message to be shown when it is a draw
                    1, "Cheers, you won.", //message when player wins
                    2, "Unfortunately, you lost." ); //message when player loses

    public static final int PLAYER = 1;
    public static final int COMPUTER_PLAYER = 2;
}
