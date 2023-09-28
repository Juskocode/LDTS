package com.freitas.hero.skeleton.menuState;


import com.freitas.hero.graphics.menuGraphics.ControlMenuView;

public class ControlMenuState extends MenuState {
    private boolean gameRunning;

    public ControlMenuState(ControlMenuView observer) {
        super(observer);
        this.gameRunning = false;
    }

    @Override
    public String processCommand(int commandID) {
        if(gameRunning){
            if(commandID == 0 || commandID == 1) {return "pause";}
        }
        else{
            if(commandID == 0 || commandID == 1) {return "mainScreen";}
        }
        return "false";
    }

    public void setGameRunningTrue() {gameRunning = true;}
    public void setGameRunningFalse() {gameRunning = false;}
}
