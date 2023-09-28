package com.freitas.hero.skeleton.menuState;


import com.freitas.hero.graphics.menuGraphics.DefaultMenuView;
import com.freitas.hero.skeleton.StateInterface;

public abstract class MenuState implements StateInterface {
    protected void updateObserver(int updateInfo) { observer.update(updateInfo); }

    protected DefaultMenuView observer;

    public String processCommand(int commandID) { return "";}

    public MenuState(DefaultMenuView observer) { this.observer = observer; }

}

