package com.freitas.hero;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.freitas.hero.Position;

public class Hero {
    private Position position;
    private static int WIDTH = 75, HEIGHT = 25;


    Hero(int x, int y){
        this.position = new Position(x, y);
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }


    public Position moveUp(){
        if (position.getY() - 1 >= 0) {
            return new Position(position.getX(), position.getY() - 1);
        }
        return new Position(position.getX(), 0);
    }
    public Position moveDown(){
        if (position.getY() + 1 < HEIGHT) {
            return new Position(position.getX(), position.getY() + 1);
        }
        return new Position(position.getX(), HEIGHT - 1);
    }
    public Position moveLeft(){
        if (position.getX() - 1 >= 0) {
            return new Position(position.getX() - 1, position.getY());
        }
        return new Position(0, position.getY());
    }
    public Position moveRight(){
        if (position.getX() + 1 < WIDTH) {
            return new Position(position.getX() + 1, position.getY());
        }
        return new Position(WIDTH - 1, position.getY());
    }

    public void draw(Screen screen){
        screen.setCharacter(position.getX(), position.getY(), TextCharacter.fromCharacter('X')[0]);
    }
}
