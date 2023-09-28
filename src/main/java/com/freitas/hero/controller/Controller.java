package com.freitas.hero.controller;

import com.freitas.hero.skeleton.StateInterface;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.jetbrains.annotations.NotNull;

public class Controller {

    public Controller() {}

    public String processKey(@NotNull KeyStroke key, StateInterface state) {
        String result = "false";

        if (key.getKeyType() == KeyType.Escape) result = state.processCommand(0);
        else if (key.getKeyType() == KeyType.Enter) result = state.processCommand(1);
        else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'w') result = state.processCommand(2);
        else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 's') result = state.processCommand(3);
        else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'a') result = state.processCommand(4);
        else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'd') result = state.processCommand(5);
        else if (key.getKeyType() == KeyType.Character && key.getCharacter() == ' ') result = state.processCommand(6);

        return result;
    }

}
