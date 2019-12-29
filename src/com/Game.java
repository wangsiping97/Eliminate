package com;

import javax.swing.*;
import com.GameManager.Matrix;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Font;

public class Game extends JFrame {
    Font font = new Font("Yuppy SC", Font.BOLD, 48);
    MusicPlayer bgm = new MusicPlayer("进行", true);
    GameManager manager;
    JPanel gamePanel;
    JPanel statusPanel;
    JLabel x = new JLabel("", JLabel.CENTER);
    JPanel y = new JPanel();
    Pad[][] cells = new Pad[11][9];
    JLabel[] nTargets;
    JLabel nStep;
    ButtonPanel status = new ButtonPanel("step");
    private Container contain;
    private static ImageIcon bgImage = new ImageIcon("img/bg2.png");
    boolean isFinished = false;
    Game(int level) throws Exception {
        bgm.start();
        manager = new GameManager(level);
        nTargets = new JLabel[manager.getMax()];
        this.setSize(800, 650);
        this.setLocationRelativeTo(null);
        contain = this.getContentPane();
        ((JPanel) contain).setOpaque(false);
        gamePanel = new JPanel(new GridLayout(11, 9));
        gamePanel.setPreferredSize(new Dimension(450, 550));
        gamePanel.setBackground(null);
        gamePanel.setOpaque(false);
        statusPanel = new JPanel(new GridLayout(4, 2));
        addTarget();
        statusPanel.setPreferredSize(new Dimension(350, 550));
        statusPanel.setBackground(null);
        statusPanel.setOpaque(false);
        addBgImage();
        for (int i = 0; i < 11; ++i) {
            for (int j = 0; j < 9; ++j) {
                cells[i][j] = new Pad(manager);
                cells[i][j].label = manager.m(i, j);
                cells[i][j].x = i;
                cells[i][j].y = j;
                cells[i][j].setVisible(true);
                cells[i][j].addCommand();
                gamePanel.add(cells[i][j]);
            }
        }
        cells[10][4].label = manager.getPlate();
        cells[10][4].repaint();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        contain.add(gamePanel, BorderLayout.EAST);
        contain.add(statusPanel, BorderLayout.WEST);
        x.setPreferredSize(new Dimension(800, 50));
        x.setOpaque(false);
        y.setPreferredSize(new Dimension(800, 50));
        y.setOpaque(false);
        JButton exit = new JButton("返回");
        JButton reset = new JButton("重置");
        y.add(exit);
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    new Level();
                } catch (Exception ex) {}
                closeThis();
            }
        });
        reset.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    new Game(level);
                } catch (Exception ex) {}
                closeThis();
            }
        });
        y.add(reset);
        contain.add(x, BorderLayout.NORTH);
        contain.add(y, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void closeThis() {
        bgm.stop();
        this.dispose();
    }

    public void addBgImage() {
        Image suitableImg = bgImage.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        ImageIcon bgimg = new ImageIcon(suitableImg);
        JLabel imgLabel = new JLabel(bgimg);
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
    }

    public void addTarget() {
        nStep = new JLabel(manager.getStep() + "", JLabel.CENTER);
        nStep.setFont(font);
        nStep.setForeground(Color.WHITE);
        statusPanel.add(status);
        statusPanel.add(nStep);
        int[] target = manager.getTarget();
        for (int i = 0; i < (manager.getMax()); ++i) {
            if (target[i] > 0) {
                String name = i == 0 ? "1" : i == 1 ? "2" : i == 2 ? "3" : i == 3 ? "4" : "5";
                ButtonPanel tar = new ButtonPanel(name);
                statusPanel.add(tar);
                nTargets[i] = new JLabel("×\t" + target[i], JLabel.CENTER);
                nTargets[i].setFont(font);
                nTargets[i].setForeground(Color.WHITE);
                statusPanel.add(nTargets[i]);
            }
        }
    }

    public boolean success() {
        for (int i = 0; i < manager.getMax(); ++i) {
            if (manager.getTarget()[i] > 0) return false;
        }
        return true;
    }

    class Pad extends JPanel {
        public int label;
        public int x;
        public int y;
        GameManager manager;
        private Image[] imgs = new Image[20];

        Pad(GameManager _manager) {
            super();
            setBackground(null);
            setOpaque(false);
            this.manager = _manager;
            for (int i = 1; i <= 15; i++) {
                imgs[i] = new ImageIcon(String.format("img/%d.png", i)).getImage();
            }
            isFinished = false;
        }

        public void addCommand() {
            MouseListener command = new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (label != -1 && isFinished == false) {
                        ArrayList<Matrix> list = manager.oneClick(x, y);
                        Thread t = new Thread(new Runnable() {
                            @Override public void run() {
                                for (Matrix m: list) {
                                    boolean isBoom = false;
                                    switch (m.flag) {
                                        case 5:
                                            try {
                                                MusicPlayer case5 = new MusicPlayer("爆炸", false);
                                                case5.start();
                                            } catch (Exception ex) {}
                                            break;
                                        case 4:
                                            try {
                                                MusicPlayer case4 = new MusicPlayer("激光", false);
                                                case4.start();
                                            } catch (Exception ex) {}
                                            break;
                                        case 3:
                                            try {
                                                MusicPlayer case3 = new MusicPlayer("冲击波", false);
                                                case3.start();
                                            } catch (Exception ex) {}
                                            break;
                                        case 9:
                                            try {
                                                MusicPlayer case5 = new MusicPlayer("爆炸", false);
                                                MusicPlayer case4 = new MusicPlayer("激光", false);
                                                case4.start();
                                                case5.start();
                                            } catch (Exception ex) {}
                                            break;
                                        case 8:
                                            try {
                                                MusicPlayer case5 = new MusicPlayer("爆炸", false);
                                                MusicPlayer case3 = new MusicPlayer("冲击波", false);
                                                case3.start();
                                                case5.start();
                                            } catch (Exception ex) {}
                                            break;
                                        case 7:
                                            try {
                                                MusicPlayer case5 = new MusicPlayer("爆炸", false);
                                                case5.start();
                                            } catch (Exception ex) {}
                                            break;
                                        default: Game.this.x.setText("");break;
                                    }
                                    for (int i = 0; i < 9; ++i) {
                                        for (int j = 0; j < 9; ++j) {
                                            cells[i][j].label = m.mat[i][j];
                                            if (cells[i][j].label == 10) {
                                                isBoom = true;
                                            }
                                            cells[i][j].repaint();
                                        }
                                    }
                                    cells[10][4].label = manager.getPlate();
                                    cells[10][4].repaint();
                                    for (int i = 0; i < manager.getMax(); ++i) {
                                        if (m.target[i] >= 0) {
                                            nTargets[i].setText("×\t" + m.target[i]);
                                        }
                                    }
                                    nStep.setText(manager.getStep() + "");

//                                    if (success()) {
//                                        status.setName("win");
//                                        status.repaint();
//                                        if (!isFinished) {
//                                            try {
//                                                bgm.stop();
//                                                MusicPlayer win = new MusicPlayer("胜利", false);
//                                                win.start();
//                                            } catch (Exception ex) {
//                                            }
//                                        }
//                                        isFinished = true;
//                                    }
//                                    else if (manager.getStep() == 0) {
//                                        status.setName("lose");
//                                        status.repaint();
//                                        if (!isFinished) {
//                                            try {
//                                                bgm.stop();
//                                                MusicPlayer lose = new MusicPlayer("失败", false);
//                                                lose.start();
//                                            } catch (Exception ex) {
//                                            }
//                                        }
//                                        isFinished = true;
//                                    }
                                    try {
                                        if (isBoom) Thread.sleep(300); else Thread.sleep(100);
                                    } catch (Exception ex) {System.out.print("exxxx");}
                                }
                                if (success()) {
                                    status.setName("win");
                                    status.repaint();
                                    if (!isFinished) {
                                        try {
                                            bgm.stop();
                                            MusicPlayer win = new MusicPlayer("胜利", false);
                                            win.start();
                                        } catch (Exception ex) {
                                        }
                                    }
                                    isFinished = true;
                                }
                                else if (manager.getStep() == 0) {
                                    status.setName("lose");
                                    status.repaint();
                                    if (!isFinished) {
                                        try {
                                            bgm.stop();
                                            MusicPlayer lose = new MusicPlayer("失败", false);
                                            lose.start();
                                        } catch (Exception ex) {
                                        }
                                    }
                                    isFinished = true;
                                }
                            }
                        });
                        t.start();
                    }
                }
            };
            addMouseListener(command);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int w1,w2,h1,h2,w,h;
            double margin = 0.05;
            w = this.getWidth();
            h = this.getHeight();
            w1 = (int)(w * margin);
            w2 = (int)(w * (1.-margin));
            h1 = (int)(h * margin);
            h2 = (int)(h * (1.-margin));

            switch (label) {
                case -1: g.drawImage(imgs[15], w1, h1, w2-w1, h2-h1, this); break;
                default: g.drawImage(imgs[label], h1, h1, h2-h1, h2-h1, this);
            }
        }
    }
    public static void main (String[] args) throws Exception {
        new Game(1);
    }
}