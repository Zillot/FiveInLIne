package com.koma.filforever.RootFolder.Data;

import com.koma.filforever.RootFolder.Data.LastGame;
import com.koma.filforever.RootFolder.GamePages.GameMenu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UndoService {
    public static UndoService I = new UndoService();

    public List<String> savesForUndo;

    public UndoService() {
        Reset();
    }

    public void Reset() {
        savesForUndo = new ArrayList<String>();
    }

    public void SaveNewUndo(GameMenu game) {
        LastGame newLastGame = new LastGame();

        newLastGame.Field = game.Cubes;
        newLastGame.Queue = game.CubesToCreate;
        newLastGame.Score = DataControll.Score;
        newLastGame.GameType = game.GamaType;
        newLastGame.UndoCharges = game.UndoCharges;
        newLastGame.PlusCubeCharges = game.PlusCubeCharges;

        savesForUndo.add(newLastGame.GetAsString());

        if (savesForUndo.size() > DataControll.MaxUndoLenght) {
            savesForUndo.remove(0);
        }
    }

    public Boolean DoUndo(GameMenu game) {
        if (savesForUndo.size() <= 0) {
            return false;
        }

        LastGame lastGameData = new LastGame();
        lastGameData.InitWithString(savesForUndo.get(savesForUndo.size() - 1));

        game.Cubes = lastGameData.Field;
        game.CubesToCreate = lastGameData.Queue;
        DataControll.Score = lastGameData.Score;
        game.GamaType = lastGameData.GameType;
        game.PlusCubeCharges = lastGameData.PlusCubeCharges;

        savesForUndo.remove(savesForUndo.size() - 1);

        return true;
    }

    public Object DeepClone(Object object) {
        return null;
    }

    //PC only
    /*public T DeepClone<T>(T obj)
    {
        using (var ms = new MemoryStream())
        {
            var formatter = new BinaryFormatter();
            formatter.Serialize(ms, obj);
            ms.Position = 0;

            return (T)formatter.Deserialize(ms);
        }
    }*/
}
