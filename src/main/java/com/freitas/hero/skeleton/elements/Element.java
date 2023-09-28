package com.freitas.hero.skeleton.elements;
import com.freitas.hero.skeleton.Position;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public abstract class Element {
    protected Position position;

    public Element(int x, int y) {position = new Position(x, y);}

    public void setPosition(Position position) {this.position = position;}

    public Position getPosition() {return position;}

    public abstract void draw(TextGraphics graphics) throws IOException;
}
