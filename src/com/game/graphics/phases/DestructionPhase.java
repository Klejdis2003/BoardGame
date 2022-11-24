package com.game.graphics.phases;

import com.game.Main;
import com.game.management.PlayerIds;
import com.game.tools.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DestructionPhase{
    private final Board board = Main.gameSession.getBoard();
    private Timer timer;
    DestructionPhase()  {
        JLabel label = Main.gameSession.getStartingPhase().getStatusLabel();
        JPanel iconPanel = Main.gameSession.getDeploymentPhase().getIconPanel();
        Main.gameSession.getStartingPhase().getMainPanel().remove(iconPanel);
        label.setText("Destruction Phase");
        board.resetCurrentPlayer();
        if(board.currentPlayer == PlayerIds.COMPUTER_PLAYER) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.gameSession.computerPlayer.destroyRandomly();
        }


        timer = new Timer(60,action -> {
            if(board.currentPlayer == PlayerIds.PLAYER) markDestructiblePositions();
        });
        timer.start();
        Main.gameSession.getCurrentWindow().repaint();

    }

    private void markDestructiblePositions(){
        JPanel boardPanel = Main.gameSession.getDeploymentPhase().getBoardPanel();
        boolean oneFound = false;
        for(int i = 0; i < board.size(); i++){
            JLabel towerLabel = (JLabel) boardPanel.getComponent(i);
            Board.Tower tower = board.get(i);
            if(tower.isDestructible()){
                oneFound = true;
                towerLabel.addMouseListener(new LabelMouseListener());
                towerLabel.setBackground(Color.GREEN);
                Main.gameSession.getCurrentWindow().repaint();
            }
            else towerLabel.setBackground(Main.gameSession.getCurrentWindow().getBackground());

        }
        if(!oneFound) { //player cannot destroy anymore
            Main.gameSession.getBoard().nextTurn();
            Main.gameSession.computerPlayer.destroyRandomly();
            if(Main.gameSession.computerPlayer.cannotPlay()) { //computer cannot destroy anymore
                JLabel label = Main.gameSession.getStartingPhase().getStatusLabel();
                int winner = board.decideWinner(); //game ends
                label.setText("Game ended. " + PlayerIds.GAME_END_MESSAGES.get(winner));
                JButton startNewGameButton = new JButton("New Game");
                JPanel buttonPanel =  new JPanel(new GridBagLayout());
                Main.gameSession.getStartingPhase().getMainPanel().setLayout(new GridBagLayout());
                Main.gameSession.getStartingPhase().getMainPanel().remove(boardPanel);
                GridBagConstraints gc = new GridBagConstraints();
                gc.gridy = 1;
                Main.gameSession.getStartingPhase().getMainPanel().add(boardPanel, gc);
                gc.gridy = 2;
                startNewGameButton.addActionListener(new StartNewGameActionListener());
                Main.gameSession.getStartingPhase().getMainPanel().add(startNewGameButton, gc);
                timer.stop();
            }
        }
        Main.gameSession.getCurrentWindow().repaint();
    }
    private class LabelMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            Icon icon = label.getIcon();
            if(icon != null) {
                int iconValue = Integer.parseInt(icon.toString());
                board.destroy(iconValue);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                int currentPlayer = board.currentPlayer;
                Main.gameSession.computerPlayer.destroyRandomly();

            }
            label.setOpaque(false);
            label.setIcon(null);
            label.removeMouseListener(this);
        }
    }
    private class StartNewGameActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Main.gameSession.newGame();
        }
    }
}
