package com.freitas.hero.graphics.menuGraphics;

import com.freitas.hero.skeleton.Position;
import com.googlecode.lanterna.graphics.TextGraphics;


public abstract class DefaultMenuView {
    protected final int width;
    protected final int height;
    protected Position selectorPos;

    public Position getSelectorPos() { return selectorPos; }
    public void setSelectorPos(int x, int y) { selectorPos.setX(x); selectorPos.setY(y); }

    public DefaultMenuView(int width, int height){
        this.width = width;
        this.height = height;
        selectorPos = new Position(width/2-6,height/2);
    }

    public void draw(TextGraphics graphics) {}

    public void update(int updateInfo) {}
}
