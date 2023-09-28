package com.freitas.hero.graphics.menuGraphics;

import com.freitas.hero.skeleton.Position;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class ControlMenuView extends DefaultMenuView {

    public ControlMenuView(int width, int height){
        super(width, height);
        selectorPos = new Position(width/2-6,height/3+14);
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#0f0f0f"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(width/2 - 6, height / 4), "CONTROLS");
        graphics.putString(new TerminalPosition(width/2 - 6, height / 3+2), "W : move up");
        graphics.putString(new TerminalPosition(width/2 - 6, height / 3+4), "A : move left");
        graphics.putString(new TerminalPosition(width/2 - 6, height / 3+6), "S : move down");
        graphics.putString(new TerminalPosition(width/2 - 6, height / 3+8), "D : move right");
        graphics.putString(new TerminalPosition(width/2 - 4, height / 3+14), "BACK");

        graphics.putString(new TerminalPosition(selectorPos.getX(), selectorPos.getY()), "->");
    }
}

