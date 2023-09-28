package com.freitas.hero.graphics.menuGraphics;

import com.freitas.hero.skeleton.Position;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class PauseMenuView extends DefaultMenuView {

    public PauseMenuView(int width, int height){
        super(width,height);
        selectorPos = new Position(width/2-6,height/2);
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#01579B"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(width/2 - 4, height / 3), "PAUSE");
        graphics.putString(new TerminalPosition(width/2 - 2, height / 2), "CONTINUE");
        graphics.putString(new TerminalPosition(width/2 - 2, height / 2 + 2), "CONTROLS");
        graphics.putString(new TerminalPosition(width/2 - 2, height / 2 + 4), "EXIT GAME");
        graphics.putString(new TerminalPosition(selectorPos.getX(), selectorPos.getY()), "->");
    }

    public void update(int updateInfo) {
        switch (updateInfo){
            case 0 -> selectorPos.setY(height/2);
            case 1 -> selectorPos.setY(height/2+2);
            case 2 -> selectorPos.setY(height/2+4);
        }
    }

}