package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Start extends JFrame {
    MusicPlayer bgm = new MusicPlayer("开场", true);
    JPanel startPanel = new JPanel(new GridLayout(2, 1, 500, 100));
    ButtonPanel startButton = new ButtonPanel("start");
    ButtonPanel exitButton = new ButtonPanel("exit");
    private Container contain;
    private static ImageIcon bgImage = new ImageIcon("img/bg0.jpeg");
    Start()throws Exception {
        bgm.start();
        startPanel.setBackground(null);
        startPanel.setOpaque(false);
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    new Level();
                } catch (Exception ex) {}
                closeThis();
            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                closeThis();
                return;
            }
        });
        startPanel.add(startButton);
        startPanel.add(exitButton);
        this.setSize(new Dimension(800, 650));
        this.addBgImage();
        this.setLocationRelativeTo(null);
        contain = this.getContentPane();
        ((JPanel) contain).setOpaque(false);
        contain.add(startPanel, BorderLayout.CENTER);
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
    public static void main (String[] args) {
        try {
            new Start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}