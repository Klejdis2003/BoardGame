package com.game.graphics;
import javax.swing.*;
import java.awt.*;

public class Tile extends JLabel {
    public Tile() {
        setPreferredSize(new Dimension(50, 50));
        setMaximumSize(new Dimension(50, 50));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }
}
