package com.freitas.hero.skeleton.elements;

import com.freitas.hero.skeleton.Position;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;


public class Bomb extends Element {
    private final int explosionSize;
    private int timeToDetonate;
    private boolean detonated;

    public Bomb(int x, int y, int size) {
        super(x,y);
        this.explosionSize = size;
        this.timeToDetonate = 300;
        this.detonated = false;
    }

    public void setPosition(Position position) {this.position = position;}
    public void decreaseTime() {this.timeToDetonate--;}

    public Position getPosition() {return position;}
    public int getExplosionSize() {return explosionSize;}
    public int getTimeToDetonate() {return timeToDetonate;}

    public boolean isDetonated() {
        return detonated;
    }

    public void setTimeToDetonate(int timeToDetonate) {
        this.timeToDetonate = timeToDetonate;
    }

    public void checkDetonated(){
        if(timeToDetonate<=0) detonated = true;
    }

    public void draw(TextGraphics graphics) {

        checkDetonated();

        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        if(!detonated) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#ff0000"));
            graphics.putString(new TerminalPosition(position.getX(), position.getY()), "0");
        }
        else{
            graphics.setForegroundColor(TextColor.Factory.fromString("#00ffff"));
            graphics.putString(new TerminalPosition(position.getX(), position.getY()), "+");
                graphics.putString(new TerminalPosition(position.getX()+1, position.getY()), "O");
                graphics.putString(new TerminalPosition(position.getX()-1, position.getY()), "O");
                graphics.putString(new TerminalPosition(position.getX(), position.getY() + 1), "O");
                graphics.putString(new TerminalPosition(position.getX() + 2*1, position.getY()), "X");
                graphics.putString(new TerminalPosition(position.getX() - 2*1, position.getY()), "X");
                graphics.putString(new TerminalPosition(position.getX(), position.getY() + 2*1), "X");
                graphics.putString(new TerminalPosition(position.getX()+1, position.getY()-1), "O");
                graphics.putString(new TerminalPosition(position.getX()+1, position.getY()+1), "O");
                graphics.putString(new TerminalPosition(position.getX()-1, position.getY() + 1), "O");
                graphics.putString(new TerminalPosition(position.getX()-1, position.getY()- 1), "O");

                if(position.getY()-1>3) {
                    graphics.putString(new TerminalPosition(position.getX(), position.getY() - 1), "O");
                    graphics.putString(new TerminalPosition(position.getX(), position.getY() - 2*1), "X");

                }

        }
    }

}
