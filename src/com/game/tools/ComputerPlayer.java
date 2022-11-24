package com.game.tools;

import com.game.Main;
import com.game.management.PlayerIds;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends Player{
    public ComputerPlayer(){
        super();
    }
    private boolean cannotPlay;
    public boolean cannotPlay(){
        return  cannotPlay;
    }
    public void insertRandomly(){
        if(getRemainingNumbers().size() > 0) {
            Board board = Main.gameSession.getBoard();
            ArrayList<Integer> availableNumbers = getRemainingNumbers();
            ArrayList<Integer> availableBoardPositions = board.getAvailablePositions();
            Random random = new Random();
            int r1, r2;
            r1 = random.nextInt(0, availableNumbers.size());
            r2 = random.nextInt(0, availableBoardPositions.size());
            int number = availableNumbers.get(r1);
            int position = availableBoardPositions.get(r2);
            board.insert(number, position);
            getRemainingNumbers().remove((Integer) number); //want to remove number from array, not value at index = number, so it's cast to Integer
            availableBoardPositions.remove((Integer) position);
            insertIconInBoard(number, position);
        }
    }

    private void insertIconInBoard(int iconValue, int position){
        JPanel boardPanel = Main.gameSession.getDeploymentPhase().getBoardPanel();
        JLabel label = (JLabel) boardPanel.getComponent(position);
        ImageIcon imageIcon = new ImageIcon(
                String.format("Icons/%d2.png", iconValue), String.valueOf(iconValue));
        label.setIcon(imageIcon);
        label.setTransferHandler(null);
        label.repaint();
        Main.gameSession.getCurrentWindow().repaint();
    }

    public void destroyRandomly(){
        if(canDestroy()) {
            Random random = new Random();
            ArrayList<Board.Tower> destroyableTowers = Main.gameSession.getBoard().getDestroyableTowers();
            int indexToDestroy = random.nextInt(0, destroyableTowers.size());
            Board.Tower towerToDestroy = destroyableTowers.get(indexToDestroy);
            int position = Main.gameSession.getBoard().destroy(towerToDestroy);
            deleteIconFromBoard(position);
        }
        else{
            int currentPlayer = Main.gameSession.getBoard().currentPlayer;
            cannotPlay = true;
            Main.gameSession.getBoard().nextTurn();
            Main.gameSession.getStartingPhase().getStatusLabel().setText("Destruction Phase, Opponent cannot destroy anymore");
            Main.gameSession.getCurrentWindow().repaint();
        }

    }
    private void deleteIconFromBoard(int position){
        JPanel boardPanel = Main.gameSession.getDeploymentPhase().getBoardPanel();
        JLabel towerLabel = (JLabel) boardPanel.getComponent(position);
        Main.gameSession.getStartingPhase().getStatusLabel().
                setText(String.format( "Destruction Phase, %s destroyed %s",
                        "Opponent", towerLabel.getIcon().toString()));
        towerLabel.setIcon(null);
        Main.gameSession.getCurrentWindow().repaint();
    }
}
