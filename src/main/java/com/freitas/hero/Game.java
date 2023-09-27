package com.freitas.hero;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;

import javax.swing.*;
import java.io.IOException;
import java.rmi.server.ExportException;

import com.freitas.hero.Hero;

public class Game{

    private Screen screen;

    private TerminalSize terminalSize;
    private static int WIDTH = 75, HEIGHT = 25;

    Hero hero = new Hero(10, 10);

    Game() throws IOException{
        try{
            this.terminalSize = new TerminalSize(WIDTH, HEIGHT);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void moveHero(Position position) {
        hero.setPosition(position);
    }

    private void processKey(KeyStroke key) throws IOException {
        switch (key.getKeyType()){
            case ArrowUp -> moveHero(hero.moveUp());
            case ArrowDown -> moveHero(hero.moveDown());
            case ArrowLeft -> moveHero(hero.moveLeft());
            case ArrowRight -> moveHero(hero.moveRight());
            case Character -> {
                if(key.getCharacter() == 'q'){
                    try{
                        screen.close();
                    } catch (ExportException e){
                        e.printStackTrace();
                    }
                }
            }
            case EOF -> System.exit(0);
        }
    }

    private void draw() throws IOException {
        try{
        screen.clear();
        hero.draw(screen);
        screen.refresh();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void run() throws IOException {
        while(true){
            try{
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);
                System.out.println(hero.getPosition().getX() + " -> " + hero.getPosition().getY());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
