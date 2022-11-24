package com.game.graphics.phases;

import com.game.Main;
import com.game.management.PlayerIds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingPhase {

    protected DeploymentPhase deploymentPhase;
    private JPanel mainPanel, topPanel;
    private JLabel statusLabel;
    private JButton startGameButton;
    private final InitializationPhase currentWindow = Main.gameSession.getCurrentWindow();

    public StartingPhase() {
        Main.gameSession.setStartingPhase(this);
        startGame();
    }
    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void startGame() {
        currentWindow.setLayout(new GridBagLayout());
        String startingPlayer = PlayerIds.MAPPING.get(Main.gameSession.startingPlayer);
        currentWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        statusLabel = new JLabel("Flipping Coin...");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 25));
        topPanel.add(statusLabel);
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new StartGameActionListener());

        Timer timer = new Timer(1200, action -> {
            statusLabel.setText(startingPlayer + " starts");
            gc.gridx = 0;
            gc.gridy = 1;
            topPanel.add(startGameButton, gc);
        });
        timer.setRepeats(false);
        timer.start();

        mainPanel.add(topPanel, Component.CENTER_ALIGNMENT);
        currentWindow.add(mainPanel);
        currentWindow.repaint();
        currentWindow.setVisible(true);
    }


    private class StartGameActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            deploymentPhase = new DeploymentPhase();
            topPanel.remove((Component) e.getSource());

        }
    }


}
