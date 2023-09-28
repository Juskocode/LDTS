package com.freitas.hero.skeleton.menuState;

import com.freitas.hero.graphics.menuGraphics.PauseMenuView;

public class PauseMenuState extends MenuState {
    private int selectorID;

    public int getSelectorID() { return selectorID; }
    public void setSelectorID(int setTo) { if (setTo >= 0 && setTo <= 2) selectorID = setTo; }

    public PauseMenuState(PauseMenuView observer) {
        super(observer);
        this.selectorID = 0;
    }

    @Override
    public String processCommand(int commandID) {
        String result = "false";

        switch (commandID) {
            case 2 -> { if (selectorID > 0) selectorID--; }
            case 3 -> { if (selectorID < 2 ) selectorID++; }
            case 1 -> {
                if (selectorID==0) result = "play";
                else if(selectorID==1) result = "controls";
                else result =  "mainScreen";
            }
        }
        updateObserver(selectorID);

        return result;
    }
}
