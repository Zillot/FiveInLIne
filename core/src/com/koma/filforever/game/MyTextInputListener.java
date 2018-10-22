package com.koma.filforever.game;

import com.badlogic.gdx.Input;

import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.SaveLoadService;

public class MyTextInputListener implements Input.TextInputListener {
    @Override
    public void input(String text) {
        if (text.length() > 0) {
            DataControll.I.PlayerName = text;
        }

        SaveLoadService.I.changeName(DataControll.I.PlayerName, null);
        SaveLoadService.I.SaveSetups();
    }

    @Override
    public void canceled() {

    }
}
