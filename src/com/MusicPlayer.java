package com;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

public class MusicPlayer extends Thread {
    boolean isLoop;
    File music;
    Player player;
    MusicPlayer(String name, boolean loop) throws FileNotFoundException, JavaLayerException {
        super();
        isLoop = loop;
        music = new File("bgm/" + name + ".mp3");
        player = new Player(new BufferedInputStream(new FileInputStream(music)));
    }
    public void play() throws Exception {
        player.play();
    }
    public void loop() throws Exception {
        while (true) {
            player = new Player(new BufferedInputStream(new FileInputStream(music))); 
            player.play();
        }
    }

    public void run() {
        super.run();
        if (isLoop == false) {
            try {
                play();
            } catch(Exception e) {}
        }
        else {
            try {
                loop();
            } catch (Exception e) {}
        }
    }
    public static void main(String [] args) throws FileNotFoundException, JavaLayerException {
        Player player;
        File music = new File ("bgm/胜利.mp3");
        BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
        player = new Player(buffer);
        player.play();
    }
}