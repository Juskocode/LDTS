package com.freitas.hero.skeleton.elements;

import com.freitas.hero.skeleton.Position;
import com.freitas.hero.skeleton.elements.Element;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

public class Hero extends Element {
    private int coins;
    private int lives;
    public Hero(int x, int y) {
        super(x, y);
        this.coins = 0;
        this.lives = 0;
    }

    public void setPosition(Position position) {this.position = position;}
    public void setCoins(int coins) {this.coins = coins;}
    public void heroAddCoin() {
        coins++;
        if(coins==20){
            coins = 0;
            lives++;
        }
    }

    public Position getPosition(){return position;}
    public int getLives(){return lives;}
    public int getCoins(){return coins;}

    public Position moveUp(){
        return new Position(position.getX(), position.getY() - 1);
    }
    public Position moveDown(){
        return new Position(position.getX(), position.getY() + 1);
    }
    public Position moveLeft(){
        return new Position(position.getX() - 1, position.getY());
    }
    public Position moveRight(){
        return new Position(position.getX() + 1, position.getY());
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#5e8d63"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#ff0000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "P");
    }

}