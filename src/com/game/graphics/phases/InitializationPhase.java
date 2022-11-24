package com.game.graphics.phases;

import com.game.Main;
import com.game.management.GameSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitializationPhase extends JFrame{
    private final JPanel panel;
    private final JButton initializationButton;

    public JPanel getPanel() {
        return panel;
    }

    public JButton getInitializationButton() {
        return initializationButton;
    }

    public InitializationPhase(){
        GameSession gameSession = Main.gameSession;
        gameSession.setInitializationPhase(this);
        setTitle("Board Game");
        setMinimumSize(new Dimension(500, 350));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        panel = new JPanel();
        initializationButton = new JButton(
                "<html>"+
                        "Welcome to our Board Game.<br>" +
                        "Click anywhere to toss the coin.<br>" +
                        "Hope you like it.<br>" +
                        "</html>");

        initializationButton.setFont(new Font("Arial", Font.BOLD, 40));
        initializationButton.setFocusPainted(false);
        initializationButton.setPreferredSize(new Dimension(500, 300));
        initializationButton.setOpaque(false);
        initializationButton.setContentAreaFilled(false);
        initializationButton.setBorder(null);
        initializationButton.setBorderPainted(false);
        initializationButton.addActionListener(new FlipCoinActionListener());

        add(initializationButton);
        setSize(200, 70);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
    private class FlipCoinActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            getContentPane().remove(button);
            button = null;
            new StartingPhase();
            repaint();
        }
    }
}

