package com.game.graphics.phases;

import com.game.Main;
import com.game.graphics.Tile;
import com.game.management.PlayerIds;
import com.game.tools.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class DeploymentPhase {
    private final Board board = Main.gameSession.getBoard();
    public Timer loop;
    private final JPanel boardPanel;
    private final JPanel iconPanel;

    public JPanel getBoardPanel() {
        return boardPanel;
    }
    public JPanel getIconPanel(){
        return  iconPanel;
    }

    DeploymentPhase() {
        Main.gameSession.setDeploymentPhase(this);
        JLabel label = Main.gameSession.getStartingPhase().getStatusLabel();
        label.setText("Deployment phase, insert a number in an available position");
        boardPanel = generateBoardPanel();
        iconPanel = generateIconPanel();
        Main.gameSession.getCurrentWindow().setMinimumSize(new Dimension(1100, 300));
        Main.gameSession.getStartingPhase().getMainPanel().add(boardPanel);
        Main.gameSession.getStartingPhase().getMainPanel().add(iconPanel);
        Main.gameSession.getCurrentWindow().repaint();
        if(Main.gameSession.startingPlayer == PlayerIds.COMPUTER_PLAYER) Main.gameSession.computerPlayer.insertRandomly();

        runGameLoop();
    }

    private void runGameLoop() {
        Timer timer;
        timer = new Timer(60, null);
        timer.addActionListener(action -> {
            if (board.isFull()) {
                new DestructionPhase();
                timer.stop();
                return;
            }
            update();
        });
        loop = timer;
        timer.start();

    }

    private void update() {
        int counter = 0;
        for (Component component : boardPanel.getComponents()) {
            JLabel label = (JLabel) component;
            Icon icon = label.getIcon();
            if (icon != null && label.getTransferHandler() != null) { //condition means that the icon has not been transferred yet
                Main.gameSession.getCurrentWindow().repaint();
                label.setTransferHandler(null); //remove transfer handler
                board.insert(Integer.parseInt(icon.toString()), counter); //add the item to game board
                Main.gameSession.computerPlayer.insertRandomly(); //after successful player entry, it's computer's turn to enter
            }
            counter++;
        }
        for (Component component : iconPanel.getComponents()) {
            JLabel label = (JLabel) component;
            Icon icon = label.getIcon();
            int iconValue = Integer.parseInt(icon.toString());
            if (!Main.gameSession.player.getRemainingNumbers().contains(iconValue)) {
                label.setEnabled(false);
                label.setTransferHandler(null);
            }

        }
    }

    protected JPanel generateBoardPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        boardPanel.setLayout(new GridLayout(1, board.size()));
        for (int i = 0; i < board.size(); i++) {
            Tile tile = new Tile();
            tile.setTransferHandler(new TransferHandler("icon"));
            tile.setOpaque(true);
            boardPanel.add(tile);
        }
        return boardPanel;
    }

    protected JPanel generateIconPanel() {
        Map<Icon, Integer> mapping = new HashMap<>();
        JPanel iconPanel = new JPanel();
        for (int i : Main.gameSession.player.getRemainingNumbers()) {
            ImageIcon icon = new ImageIcon(
                    String.format("Icons/%d1.png", i), String.valueOf(i));
            JLabel label = new JLabel(icon);

            label.setTransferHandler(new TransferHandler("icon"));
            label.addMouseListener(new DragMouseAdapter());
            iconPanel.add(label);
        }
        return iconPanel;

    }

    private static class DragMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            JLabel c = (JLabel) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            if (handler != null)
                handler.exportAsDrag(c, e, TransferHandler.COPY);
        }

    }
}
