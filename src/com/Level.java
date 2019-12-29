package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Level extends JFrame {
    MusicPlayer bgm = new MusicPlayer("开场", true);
    JPanel levelPanel = new JPanel(new GridLayout(4, 1));
    ButtonPanel exitButton = new ButtonPanel("back");
    ButtonPanel level1 = new ButtonPanel("11");
    ButtonPanel level2 = new ButtonPanel("22");
    ButtonPanel level3 = new ButtonPanel("33");
    private Container contain;
    private static ImageIcon bgImage = new ImageIcon("img/bg.png");
    Level() throws Exception {
        bgm.start();
        levelPanel.setBackground(null);
        levelPanel.setOpaque(false);
        levelPanel.setPreferredSize(new Dimension(100, 100));
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                closeThis();
                try {
                    new Start();
                } catch (Exception ex) {}
            }
        });
        level1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                closeThis();
                try {
                    new Game(1);
                } catch (Exception ex) {}
            }
        });
        level2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                closeThis();
                try {
                    new Game(2);
                } catch (Exception ex) {}
            }
        });
        level3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                closeThis();
                try {
                    new Game(3);
                } catch (Exception ex) {}
            }
        });
        levelPanel.add(level1);
        levelPanel.add(level2);
        levelPanel.add(level3);
        levelPanel.add(exitButton, BorderLayout.SOUTH);
        this.setSize(new Dimension(800, 650));
        this.addBgImage();
        this.setLocationRelativeTo(null);
        contain = this.getContentPane();
        ((JPanel) contain).setOpaque(false);
        contain.add(levelPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void addBgImage() {
        Image suitableImg = bgImage.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        ImageIcon bgimg = new ImageIcon(suitableImg);
        JLabel imgLabel = new JLabel(bgimg);
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
    }
    public void closeThis() {
        bgm.stop();
        this.dispose();
    }
}