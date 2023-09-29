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
        this.timeToDetonate = 400;
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
            graphics.putString(new TerminalPosition(position.getX(), position.getY()), "B");
        }
        else{
            graphics.setForegroundColor(TextColor.Factory.fromString("#00ffff"));
            graphics.putString(new TerminalPosition(position.getX(), position.getY()), "+");
            for(int i = 1;i <= explosionSize;   i++){
                graphics.putString(new TerminalPosition(position.getX()+i, position.getY()), "O");
                graphics.putString(new TerminalPosition(position.getX()-i, position.getY()), "O");
                graphics.putString(new TerminalPosition(position.getX(), position.getY() + i), "O");
                graphics.putString(new TerminalPosition(position.getX() + 2*i, position.getY()), "X");
                graphics.putString(new TerminalPosition(position.getX() - 2*i, position.getY()), "X");
                graphics.putString(new TerminalPosition(position.getX(), position.getY() + 2*i), "X");
                graphics.putString(new TerminalPosition(position.getX()+i, position.getY()-i), "O");
                graphics.putString(new TerminalPosition(position.getX()+i, position.getY()+i), "O");
                graphics.putString(new TerminalPosition(position.getX()-i, position.getY() + i), "O");
                graphics.putString(new TerminalPosition(position.getX()-i, position.getY()- i), "O");

                if(position.getY()-i>3) {
                    graphics.putString(new TerminalPosition(position.getX(), position.getY() - i), "O");
                    graphics.putString(new TerminalPosition(position.getX(), position.getY() - 2*i), "X");

                }
            }
        }
    }

}
