package com.freitas.hero.graphics;

import com.freitas.hero.skeleton.GameState;
import com.freitas.hero.skeleton.elements.*;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;

public class GameView {
    GameState gameState;

    public GameView(GameState gameState) {
        this.gameState = gameState;
    }

    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(gameState.getWidth(), gameState.getHeight()), ' ');

        graphics.setForegroundColor(TextColor.Factory.fromString("#ff0000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(17,1), "LIVES:");
        TextImage image = new BasicTextImage(5, 5);
        //add life
        graphics.putString(new TerminalPosition(24,1), String.format("%02d", gameState.getPlayer().getLives()));
        //
        graphics.putString(new TerminalPosition(29,1), "COINS:");
        //add coins counter
        graphics.putString(new TerminalPosition(36,1), String.format("%02d", gameState.getPlayer().getCoins()));

        //draws the lists of different methods in the display
        for (Coin coin: gameState.getCoinsMap()) coin.draw(graphics);
        for (Wall wall : gameState.getWalls()) wall.draw(graphics);
        for(Block block : gameState.getMaze()) block.draw(graphics);
        //draw the player
        gameState.getPlayer().draw(graphics);
    }

    public void setgameState(GameState gameState) {
        this.gameState = gameState;
    }
}
