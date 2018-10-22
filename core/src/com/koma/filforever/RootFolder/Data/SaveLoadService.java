package com.koma.filforever.RootFolder.Data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.RootFolder.Data.Blows.BlowCheckBase;
import com.koma.filforever.RootFolder.GamePages.GameMenu;
import com.koma.filforever.FilForeverRoot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class SaveLoadService {
    public static SaveLoadService I = new SaveLoadService();

    //PRODUCTION: переключить базу
    //private final String ServiceURL = "http://komastoragetest.azurewebsites.net/";
    private final String ServiceURL = "http://komastorage.azurewebsites.net/";

    private final String ServiceStatisticSubURL = "Statistic/";
    private final String ServiceScoreSubURL = "Score/";
    private final String ServiceUserSubURL = "User/";
    private final String ServiceMessageSubURL = "Message/";

    private final String LastGameFieldFileName = "LastGame_v2.cbr";
    private final String SetupsFileName = "Setups.cbr";
    private final String AdditionalSetupsFileName = "AddSetups.cbr";
    private final String FirstPlayGameName = "FLG.cbr";

    public SaveLoadService() {

    }

    private static void WebClientAdapter(String path, Delegate delegate) {
        if (!FilForeverRoot.IsNetworkAvailable()) {
            return;
        }

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(path);
        Gdx.net.sendHttpRequest(httpRequest, delegate);

        Gdx.net.sendHttpRequest(httpRequest, new Delegate() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String res = httpResponse.getResultAsString();
            }
        });
    }

    public void changeName(String name, Delegate handler) {
        if (!FilForeverRoot.IsNetworkAvailable()) {
            return;
        }

        String PhoneNum = EngineService.I.getDeviceId();
        String address = String.format(ServiceURL + ServiceUserSubURL + "SaveUser?IdPhone=" + PhoneNum + "&name=" + name);

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(address);
        Gdx.net.sendHttpRequest(httpRequest, handler);
    }

    public void saveStatistic(String key, String value, Delegate handler) {
        if (!FilForeverRoot.IsNetworkAvailable()) {
            return;
        }

        String address = String.format(ServiceURL + ServiceStatisticSubURL + "SaveStatistic?name=Android_" + key + "&value=" + value);

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(address);
        Gdx.net.sendHttpRequest(httpRequest, handler);
    }

    public void getScore(int type, String usertoken, Delegate handler) {
        if (!FilForeverRoot.IsNetworkAvailable()) {
            return;
        }

        handler.UserToken = usertoken;
        String PhoneNum = EngineService.I.getDeviceId();

        String address = String.format(ServiceURL + ServiceScoreSubURL + "GetScore?IdPhone=" + PhoneNum + "&type=" + type);

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(address);
        Gdx.net.sendHttpRequest(httpRequest, handler);
    }

    public void getMessage(String usertoken, Delegate handler) {
        if (!FilForeverRoot.IsNetworkAvailable()) {
            return;
        }

        handler.UserToken = "GetMessage" + usertoken;
        String PhoneNum = EngineService.I.getDeviceId();

        String address = String.format(ServiceURL + ServiceMessageSubURL + "GetMessage?IdPhone=" + PhoneNum);

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(address);
        Gdx.net.sendHttpRequest(httpRequest, handler);
    }

    public void saveScore(int type, int score, Delegate handler) {
        if (!FilForeverRoot.IsNetworkAvailable()) {
            return;
        }

        //changeName(DataControll.I.PlayerName, null);

        String PhoneNum = EngineService.I.getDeviceId();
        String address = String.format(ServiceURL + ServiceScoreSubURL + "SaveScore?IdPhone=" + PhoneNum + "&type=" + type + "&score=" + score);

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(address);
        Gdx.net.sendHttpRequest(httpRequest, handler);
    }

    public void getTopList(int type, int count, String usertoken, Delegate handler) {
        String address = String.format(ServiceURL + ServiceScoreSubURL + "GetTop?type=" + type + "&count=" + count);

        handler.UserToken = "" + usertoken;
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(address);
        Gdx.net.sendHttpRequest(httpRequest, handler);
    }

    private void BaseSaveGame(GameMenu game, String fileName, BlowCheckBase blowController) {
        //for PC, WinPhone, Android
        LastGame newLastGame = new LastGame();

        newLastGame.Field = game.Cubes;
        newLastGame.Queue = game.CubesToCreate;
        newLastGame.Score = DataControll.Score;
        newLastGame.GameType = game.GamaType;
        newLastGame.UndoCharges = game.UndoCharges;
        newLastGame.PlusCubeCharges = game.PlusCubeCharges;
        //newLastGame.savesForUndo = UndoService.I.savesForUndo;
        newLastGame.newBest = game.newBest;

        //Android Save
        try {
            if (!Gdx.files.local(fileName).exists()) {
                File file = Gdx.files.local(fileName).file();
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(Gdx.files.local(fileName).file());
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(newLastGame);

            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception ex) {

        }
    }

    public Boolean CanLoadGame(GameType type) {
        return Gdx.files.local(type + LastGameFieldFileName).exists();
    }

    public void BaseLoadGame(GameMenu game, String fileName, BlowCheckBase blowController) {
        //for PC, WinPhone, Android
        LastGame lastGameData = null;

        //Android load
        try {
            FileInputStream fis = new FileInputStream(Gdx.files.local(fileName).file());
            ObjectInputStream oin = new ObjectInputStream(fis);

            lastGameData = (LastGame) oin.readObject();

            oin.close();
            fis.close();
        } catch (Exception ex) {

        }

        //for PC, WinPhone, Android
        if (lastGameData == null) {
            return;
        }

        game.Cubes = lastGameData.Field;
        game.CubesToCreate = lastGameData.Queue;
        DataControll.Score = lastGameData.Score;
        game.GamaType = lastGameData.GameType;
        game.UndoCharges = lastGameData.UndoCharges;
        game.PlusCubeCharges = lastGameData.PlusCubeCharges;
        //UndoService.I.savesForUndo = lastGameData.savesForUndo;
        game.newBest = lastGameData.newBest;

        for (CellItem item : game.Cubes) {
            item.ReApear();
        }

        for (CellItem item : game.CubesToCreate) {
            item.ReApear();
        }
    }

    public void RemoveSave(GameType type) {
        if (Gdx.files.local(type + LastGameFieldFileName).exists()) {
            Gdx.files.local(type + LastGameFieldFileName).delete();
        }
    }

    public void SaveGame(GameMenu game, BlowCheckBase blowController) {
        if (!game.GameOver) {
            BaseSaveGame(game, game.GamaType + LastGameFieldFileName, blowController);
        }
    }

    public void LoadGame(GameMenu game, BlowCheckBase blowController) {
        BaseLoadGame(game, game.GamaType + LastGameFieldFileName, blowController);
    }

    public void SaveSetups() {
        //for PC, WinPhone, Android
        MainSetups mainSetups = new MainSetups();

        mainSetups.LanguageIndex = DataControll.LanguageIndex;
        mainSetups.BestScores = DataControll.BestScores;
        mainSetups.PlayerName = DataControll.I.PlayerName;
        mainSetups.GamePlayed = DataControll.I.GamePlayed;

        //Android Save
        try {
            if (!Gdx.files.local(SetupsFileName).exists()) {
                File file = Gdx.files.local(SetupsFileName).file();
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(Gdx.files.local(SetupsFileName).file());
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(mainSetups);

            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception ex) {

        }

        AddMainSetups AddmainSetups = new AddMainSetups();

        AddmainSetups.Sound = (int) SpriteAdapter.SelfPointer.Sound;

        //Android Save
        try {
            if (!Gdx.files.local(AdditionalSetupsFileName).exists()) {
                File file = Gdx.files.local(AdditionalSetupsFileName).file();
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(Gdx.files.local(AdditionalSetupsFileName).file());
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(AddmainSetups);

            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception ex) {

        }
    }

    public Boolean CanLoadSetups() {
        return Gdx.files.local(SetupsFileName).exists();
    }

    public void LoadSetups() {
        MainSetups mainSetups = null;

        try {
            FileInputStream fis = new FileInputStream(Gdx.files.local(SetupsFileName).file());

            ObjectInputStream oin = new ObjectInputStream(fis);

            mainSetups = (MainSetups) oin.readObject();

            oin.close();
            fis.close();
        } catch (Exception ex) {

        }

        //for PC, WinPhone, Android
        DataControll.LanguageIndex = mainSetups.LanguageIndex;
        DataControll.BestScores = new LinkedList<Integer>();

        if (mainSetups.BestScores != null) {
            for (int i = 0; i < mainSetups.BestScores.size(); i++) {
                DataControll.BestScores.add(mainSetups.BestScores.get(i));
            }

            for (int i = DataControll.BestScores.size(); i < DataControll.I.GameModes.size(); i++) {
                DataControll.BestScores.add(0);
            }
        } else {
            for (int i = 0; i < DataControll.I.GameModes.size(); i++) {
                DataControll.BestScores.add(0);
            }
        }

        DataControll.I.PlayerName = mainSetups.PlayerName;
        DataControll.I.GamePlayed = mainSetups.GamePlayed;

        if (DataControll.LanguageIndex != -1) {
            SpriteAdapter.SelfPointer.Language = SpriteAdapter.SelfPointer.AvailableLanguages.get(DataControll.LanguageIndex);
        }

        if (!Gdx.files.local(AdditionalSetupsFileName).exists()) {
            return;
        }

        AddMainSetups AddmainSetups = null;

        try {
            FileInputStream fis = new FileInputStream(Gdx.files.local(AdditionalSetupsFileName).file());

            ObjectInputStream oin = new ObjectInputStream(fis);

            AddmainSetups = (AddMainSetups) oin.readObject();
            SpriteAdapter.SelfPointer.Sound = AddmainSetups.Sound;

            oin.close();
            fis.close();
        } catch (Exception ex) {

        }
    }

    public Boolean isFirstPlay() {
        return !Gdx.files.local(FirstPlayGameName).exists();
    }

    public void setAsFirstPlayReady() {
        try {
            if (!Gdx.files.local(FirstPlayGameName).exists()) {
                File file = Gdx.files.local(FirstPlayGameName).file();
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(Gdx.files.local(FirstPlayGameName).file());
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeChars("[StatusFPG]:Allready;");

            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception ex) {

        }
    }
}
