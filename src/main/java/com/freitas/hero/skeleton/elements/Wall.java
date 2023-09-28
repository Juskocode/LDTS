package com.freitas.hero.skeleton.elements;
import com.freitas.hero.skeleton.Position;
import com.freitas.hero.skeleton.elements.Element;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall extends Element {
    public Wall(int x, int y) {super(x,y);}

    public void setPosition(Position position) {this.position = position;}

    public Position getPosition() {return position;}

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#999999"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), " ");
    }
}
