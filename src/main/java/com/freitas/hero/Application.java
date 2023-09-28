package com.freitas.hero;

import com.freitas.hero.skeleton.GameSkeleton;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        try {
            GameSkeleton gaming = new GameSkeleton(60, 30);
            gaming.run();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
