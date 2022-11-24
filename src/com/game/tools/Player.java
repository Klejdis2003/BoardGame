package com.game.tools;

import com.game.Main;

import java.util.ArrayList;

public class Player {
    private final ArrayList<Integer> remainingNumbers = new ArrayList<>(8);
    public Player(){
        for(int i = 0; i < 8; i++){
            remainingNumbers.add(i+1);
        }
    }
    public ArrayList<Integer> getRemainingNumbers() {
        return remainingNumbers;
    }

    public boolean canDestroy(){
        ArrayList<Board.Tower> destroyableTowers = Main.gameSession.getBoard().getDestroyableTowers();
        return destroyableTowers.size() > 0;
    }
}
