package com.freitas.hero.graphics.menuGraphics;

import com.freitas.hero.skeleton.Position;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.jetbrains.annotations.NotNull;


public class EndMenuView extends DefaultMenuView {
    private String endMsg;

    public EndMenuView(int width, int height){
        super(width, height);
        selectorPos = new Position(width/2-6,height/3+14);
        this.endMsg = "YOU LOST! :(";
    }

    public String getEndMsg() {
        return endMsg;
    }

    public void draw(@NotNull TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#01579B"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(width/2 - 4, height / 4), endMsg);
        graphics.putString(new TerminalPosition(width/2 - 2, height / 3+14), "BACK");
        graphics.putString(new TerminalPosition(selectorPos.getX(), selectorPos.getY()), "->");
    }

    @Override
    public void update(int updateInfo) {
        if (updateInfo == 0 ) { endMsg = "YOU LOST! :(";}
        else { endMsg = "YOU WON! :-D";}
    }

}
