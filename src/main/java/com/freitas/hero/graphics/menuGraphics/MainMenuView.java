package com.freitas.hero.graphics.menuGraphics;

import com.freitas.hero.skeleton.Position;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class MainMenuView extends DefaultMenuView {

    public MainMenuView(int width, int height){
        super(width, height);
        this.selectorPos = new Position(width/2-6,height/2);
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#01579B"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(width/2 - 4, height / 3), "HERO");
        graphics.putString(new TerminalPosition(width/2 - 2, height / 2), "PLAY");
        graphics.putString(new TerminalPosition(width/2 - 2, height / 2 + 2), "CONTROLS");
        graphics.putString(new TerminalPosition(width/2 - 2, height / 2 + 4), "QUIT");
        graphics.putString(new TerminalPosition(selectorPos.getX(), selectorPos.getY()), "->");
    }

    @Override
    public void update(int updateInfo) {
        switch (updateInfo){
            case 0 -> selectorPos.setY(height/2);
            case 1 -> selectorPos.setY(height/2+2);
            case 2 -> selectorPos.setY(height/2+4);
        }
    }

}
