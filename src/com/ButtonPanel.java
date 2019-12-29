package com;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    String name;
    ButtonPanel(String _name) {
        name = _name;
    }
    public void setName(String _name) {
        name = _name;
    }
    public void paint(Graphics g) {
        int w = 250;
        int h = 110;
        this.setOpaque(false);
        if (name == "start")
            g.drawImage(new ImageIcon("img/start.png").getImage(), 300, 75, 200, 80, this);
        else if (name == "exit")
            g.drawImage(new ImageIcon("img/exit.png").getImage(), 300, 75, 200, 80, this);
        else if (name == "11")
            g.drawImage(new ImageIcon("img/low.png").getImage(), 275, 50, w, h, this);
        else if (name == "22")
            g.drawImage(new ImageIcon("img/middle.png").getImage(), 265, 50, w + 10, h, this);
        else if (name == "33")
            g.drawImage(new ImageIcon("img/high.png").getImage(), 275, 50, w, h, this);
        else if (name == "back")
            g.drawImage(new ImageIcon("img/back.png").getImage(), 650, 25, 100, 100, this);
        else if (name == "step")
            g.drawImage(new ImageIcon("img/step.png").getImage(), 10, 0, 150, 100, this);
        else if (name == "win")
            g.drawImage(new ImageIcon("img/win.png").getImage(), 10, 0, 150, 100, this);
        else if (name == "lose")
            g.drawImage(new ImageIcon("img/lose.png").getImage(), 10, 0, 150, 100, this);
        else if (name == "1")
            g.drawImage(new ImageIcon("img/1.png").getImage(), 50, 15, 100, 100, this);
        else if (name == "2")
            g.drawImage(new ImageIcon("img/2.png").getImage(), 50, 15, 100, 100, this);
        else if (name == "3")
            g.drawImage(new ImageIcon("img/3.png").getImage(), 50, 15, 100, 100, this);
        else if (name == "4")
            g.drawImage(new ImageIcon("img/4.png").getImage(), 50, 15, 100, 100, this);
        else if (name == "5")
            g.drawImage(new ImageIcon("img/5.png").getImage(), 50, 15, 100, 100, this);
    }
}