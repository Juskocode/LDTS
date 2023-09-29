package com.freitas.hero.skeleton.elements;

import com.freitas.hero.skeleton.Position;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;


public class Enemy extends Element {

    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#ff0000"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "&");
    }

    public Position move() {
        return switch (new Random().nextInt(4)) {
            case 0 -> new Position(position.getX(), position.getY() - 1);
            case 1 -> new Position(position.getX() + 1, position.getY());
            case 2 -> new Position(position.getX(), position.getY() + 1);
            case 3 -> new Position(position.getX() - 1, position.getY());
            default -> new Position(position.getX(), position.getY());
        };
    }
}
