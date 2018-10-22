package com.koma.filforever.RootFolder.GamePages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder.InstructionBase;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.SpriteEffects;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.PivotPoint;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_ButtonControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_IMenuControll;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.GameType;
import com.koma.filforever.RootFolder.Data.InstructionsContoll;
import com.koma.filforever.RootFolder.Data.SaveLoadService;
import com.koma.filforever.game.MainGameClass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainMenu extends IGameState {
    private static Random rand = new Random();

    //FPS in motion test
    List<FloatController> floatControllers;

    public MainMenu() {
        Instructions = new LinkedList<InstructionBase>();
        InstructionIndex = 0;

        Controlls.put("StartGameButton", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2, 275), Color.DarkGray, Color.Black, "Start Game", new Vector2(300, 80), "BArial20", 34));
        Controlls.put("Achievements", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2, 375), Color.DarkGray, Color.Black, "Achievements", new Vector2(300, 80), "BArial20", 34));
        Controlls.put("Leaderboard", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2, 475), Color.DarkGray, Color.Black, "Leaderboard", new Vector2(300, 80), "BArial20", 34));

        Controlls.put("SoundButton", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2 - 110, 620), Color.DarkGray, Color.Black, "", new Vector2(80, 80), "", 34));
        Controlls.put("NameChangeButton", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2, 620), Color.DarkGray, Color.Black, "", new Vector2(80, 80), "", 34));
        Controlls.put("LanguageButton", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2 + 110, 620), Color.DarkGray, Color.Black, "", new Vector2(80, 80), "", 34));

        Controlls.get("SoundButton").onTap[0] = SoundButtonHandler;
        Controlls.get("NameChangeButton").onTap[0] = NameChangeButtonHandler;
        Controlls.get("LanguageButton").onTap[0] = LanguageButtonHandler;

        ((UI_ButtonControll) Controlls.get("SoundButton")).IcoDrawEvent = SoundButtonIco;
        ((UI_ButtonControll) Controlls.get("NameChangeButton")).IcoDrawEvent = NameChangeButtonIco;
        ((UI_ButtonControll) Controlls.get("LanguageButton")).IcoDrawEvent = LanguageButtonIco;

        Controlls.get("StartGameButton").onTap[0] = StartGameButtonTap;
        Controlls.get("Achievements").onTap[0] = AchievementsButtonTap;
        Controlls.get("Leaderboard").onTap[0] = LeaderbordButtonTap;
    }

    public Delegate StartGameButtonTap = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("SelectGameModePage", "", false);
        }
    };

    public Delegate AchievementsButtonTap = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("AchievementsPage", "", false);
        }
    };

    public Delegate LeaderbordButtonTap = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("TopLeaderbordPage", "", false);
        }
    };

    public Delegate NameChangeButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            Vector2 LeftUpCorner = position.sub(size.div(2));
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("AvatarIco"), LeftUpCorner, size, layer + 6);

            if (DataControll.I.PlayerName != null) {
                if (DataControll.I.PlayerName.contains("DefaultName")) {
                    spriteBatch.DrawText("Change your name", position.add(0, -65), Color.Black, 1, "BArial20", 1.0f, TextAlign.CenterXY, 0, layer + 6);
                }

                String line = spriteBatch.LocalizeParts("Hello, ") + DataControll.I.PlayerName + "!";
                spriteBatch.DrawText(line, position.add(0, 65), Color.Black, 1, "Arial16", 1.0f, TextAlign.CenterXY, 0, layer + 6, false);
            }
        }
    };

    public Delegate LanguageButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get(DataControll.I.Flags.get(DataControll.LanguageIndex)), position.sub(size.div(2)), size, layer);
        }
    };

    public Delegate SoundButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            if (SpriteAdapter.SelfPointer.Sound == 1) {
                DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("SoundOn"), position.sub(size.div(2)), size, 40);
            } else {
                DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("SoundOff"), position.sub(size.div(2)), size, 40);
            }
        }
    };

    public Delegate NameChangeButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("ProfilePage", "", false);
        }
    };

    public Delegate LanguageButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            DataControll.LanguageIndex++;
            if (DataControll.LanguageIndex >= DataControll.I.Flags.size()) {
                DataControll.LanguageIndex = 0;
            }

            SpriteAdapter.SelfPointer.Language = SpriteAdapter.SelfPointer.AvailableLanguages.get(DataControll.LanguageIndex);
            SpriteAdapter.SelfPointer.StringsCache.clear();

            SaveLoadService.I.SaveSetups();
        }
    };

    public Delegate SoundButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            if (SpriteAdapter.SelfPointer.Sound == 1) {
                SpriteAdapter.SelfPointer.Sound = 0;
            } else {
                SpriteAdapter.SelfPointer.Sound = 1;
            }

            SaveLoadService.I.SaveSetups();
        }
    };

    public void Initialize() {
        super.Initialize();
    }

    @Override
    public void Dispose() {
        super.Dispose();
    }

    @Override
    public void OnNavigatetFrom(Object data) {
        super.OnNavigatetFrom(data);
    }

    String line = "";

    @Override
    public void OnNavigatetTo(Object data) {
        super.OnNavigatetTo(data);

        MainGameClass.SelftPointer.CheckMessages();

        SaveLoadService.I.saveStatistic("GameLaunched", DataControll.Version, null);

        //Logic.reset();

        if (Instructions == InstructionsContoll.I.MainMenuInstructionsPart1 && InstructionIndex > 0) {
            InstructionIndex = 0;
            Instructions = InstructionsContoll.I.MainMenuInstructionsPart2;
        }

        for (int i = 0; i < DataControll.I.GameModes.size(); i++) {
            SaveLoadService.I.saveScore(i, DataControll.BestScores.get(i), null);
        }
    }

    @Override
    public void PrivateLocalTouchEventCheck(TouchEventType TouchEventName, Vector2 position) {
        if (!MainGameClass.SelftPointer.ServerMessageDialog()) {
            return;
        }

        if (InstructionIndex >= Instructions.size()) {
            super.PrivateLocalTouchEventCheck(TouchEventName, position);
        } else {
            for (UI_IMenuControll item : Instructions.get(InstructionIndex).FitControlls) {
                item.TouchEventCheck(position, TouchEventName);
            }

            if (Instructions.get(InstructionIndex).CheckTapForNextInstruction(TouchEventName, position)) {
                InstructionIndex++;
            }
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        //FPS in motion test
        /*for(FloatController item : floatControllers)
        {
            item.Update(timeDelta);

            if(item.Value == 1)
                item.GoDown();
            if(item.Value == 0)
                item.GoUp();
        }*/

        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);

        if (InstructionIndex < Instructions.size()) {
            toRet = toRet | Instructions.get(InstructionIndex).Update(timeDelta);
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch) {
        spriteBatch.Clear(Color.Beige);

        //бкграунд и полоска
        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, 215), Color.LightGray, 1, 0, 2);
        spriteBatch.FillRectangle(new Vector2(0, 165), new Vector2(DataControll.WindowWidth, 36), Color.White, 1, 0, 3);

        //шапка меню
        spriteBatch.DrawText("FIVE IN", new Vector2(DataControll.WindowWidth / 2, 55), Color.White, 1, "Arial32", 1, TextAlign.CenterXY, 0, 30);
        spriteBatch.DrawText("LINE", new Vector2(DataControll.WindowWidth / 2, 110), Color.Black, 1, "BArial72", 1, TextAlign.CenterXY, 0, 30);

        //цвета на полоске
        for (int i = 0; i < DataControll.Colors.length; i++) {
            spriteBatch.FillRectangle(new Vector2((DataControll.WindowWidth / 2) - ((DataControll.Colors.length * 40 - 20) / 2) + 40 * i, 173), new Vector2(20, 20), DataControll.Colors[i], 1, 0, 4);
        }

        super.Draw(spriteBatch);

        if (InstructionIndex < Instructions.size()) {
            Instructions.get(InstructionIndex).Draw(spriteBatch);
        }

        //spriteBatch.DrawText("Sended:" + line, new Vector2(20, 750), Color.Black, 1, "Arial20", 0.5f, TextAlign.LeftX, 0, 30);
        //spriteBatch.DrawText("Id:" + EngineService.I.getDeviceId(), new Vector2(20, 780), Color.Black, 1, "Arial20", 0.5f, TextAlign.LeftX, 0, 30);


        //position and pivots test
        /*spriteBatch.FillRectangle(new Vector2(200, 200), new Vector2(100, 100), Color.Blue, PivotPoint.LeftXUpY, 1, 0.78f, 20);
        spriteBatch.FillRectangle(new Vector2(200, 200), new Vector2(100, 100), Color.Red, PivotPoint.LeftXDownY, 1, 0.78f, 20);

        spriteBatch.FillRectangle(new Vector2(200, 200), new Vector2(100, 100), Color.Orange, PivotPoint.RightXUpY, 1, 0.78f, 20);
        spriteBatch.FillRectangle(new Vector2(200, 200), new Vector2(100, 100), Color.Green, PivotPoint.RightXDownY, 1, 0.78f, 20);

        spriteBatch.FillRectangle(new Vector2(200, 200), new Vector2(100, 100), Color.LightGray, PivotPoint.CenterXCenterY, 1, 0, 20);*/

        //FPS in motion test
        /*Vector2 pos = new Vector2(20, 100);
        int x = 0;

        for(FloatController item : floatControllers)
        {
            spriteBatch.FillRectangle(pos.add(new Vector2(440 * item.Value, 40 * x)), new Vector2(35, 35), DataControll.Colors[x % 4], PivotPoint.CenterXCenterY, item.Value, 0, 20);
            x++;

            if(x % 15 == 0)
                pos = pos.sub(new Vector2(0, 35 * x));
        }*/

        //FPS static test
        /*pos = new Vector2(38.5f, 50);

        for(int i = 0 ; i < 10; i++)
            for(int j = 0 ; j < 15; j++)
            {
                float opacity = (i + j) % 2 == 0 ? 0.4f : 0.7f;
                opacity = (i + j) % 5 == 0 ? 0.2f : opacity;

                spriteBatch.FillRectangle(pos.add(new Vector2(i * 45, j * 45)), new Vector2(40, 40), DataControll.Colors[i % 4], PivotPoint.CenterXCenterY, opacity, 0, 20);
                spriteBatch.FillRectangle(pos.add(new Vector2(i * 45 + 5, j * 45 + 5)), new Vector2(40, 40), DataControll.Colors[i % 4], PivotPoint.CenterXCenterY, opacity, 0, 20);
            }
        */
    }
}
