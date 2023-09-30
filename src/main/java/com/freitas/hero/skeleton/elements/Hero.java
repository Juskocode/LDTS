package com.freitas.hero.skeleton.elements;

import com.freitas.hero.skeleton.Position;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

public class Hero extends Element {
    private int coins;
    private int lives;
    private int bombs;
    private int bombSize;
    private int bombCooldown;
    private int movementCoolDown;

    public Hero(int x, int y) {
        super(x, y);
        this.coins = 0;
        this.lives = 0;
        this.bombs = 300;
        this.bombCooldown = 0;
        this.movementCoolDown = 0;
    }

    public void setPosition(Position position) {this.position = position;}
    public void setCoins(int coins) {this.coins = coins;}
    public void heroAddCoin() {
        coins++;
        if(coins == 10){
            coins = 0;
            lives++;
        }
    }

    public Position getPosition(){return position;}
    public int getBombSize(){return bombSize;}

    public int getBombPlaceCoolDown(){return bombCooldown;}
    public int getMovementCoolDown() {return movementCoolDown;}
    public void BombPlaceCoolDownSubtract() {bombCooldown--;}

    public int getLives(){return lives;}
    public int getCoins(){return coins;}
    public int getBombs(){return bombs;}
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
    public void MovementCoolDownBegin() {this.movementCoolDown = 8;}
    public void MovementCoolDownSubtract() {movementCoolDown--;}
    public Position placeBomb(){
        bombCooldown = 2;
        bombs--;
        return new Position(position.getX(),position.getY());
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#0000ff"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "H");
    }

}