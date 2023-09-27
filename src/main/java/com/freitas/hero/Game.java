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

public class Game{

    private Screen screen;

    private TerminalSize terminalSize;
    private static int WIDTH = 75, HEIGHT = 25;

    private int x, y;

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
    private boolean checkBoundories(int x, int y){
        return (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT);
    }
    private void processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.ArrowUp){
            if (y - 1 >= 0) {
                y--;
                return;
            }
            y = 0;

        }
        else if (key.getKeyType() == KeyType.ArrowDown){
            if (y + 1 < HEIGHT) {
                y++;
                return;
            }
            y = HEIGHT - 1;
        }
        else if (key.getKeyType() == KeyType.ArrowLeft){
            if (x - 1 >= 0) {
                x--;
                return;
            }
            x = 0;
        }
        else if (key.getKeyType() == KeyType.ArrowRight){
            if (x + 1 < WIDTH) {
                x++;
                return;
            }
            x = WIDTH - 1;
        }
        else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
            try{
                screen.close();
            } catch (ExportException e){
                e.printStackTrace();
            }
        }
        else if(key.getKeyType() == KeyType.EOF){
            System.exit(0);
        }
    }

    private void draw() throws IOException {
        try{
        screen.clear();
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')
                [0]);
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
                System.out.println(x + " -> " + y);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
