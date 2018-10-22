package com.koma.filforever.RootFolder.GamePages;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_ButtonControll;

import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.InstructionsContoll;
import com.koma.filforever.RootFolder.Data.SaveLoadService;

import java.util.Random;

public class LanguageSelectPage extends IGameState {
    private static Random rand = new Random();

    public LanguageSelectPage() {
        Controlls.put("ENButton", new UI_ButtonControll(new Vector2(240, 235), Color.LightGray, Color.Black, "", new Vector2(150, 150), "", 40));
        Controlls.put("UAButton", new UI_ButtonControll(new Vector2(240, 405), Color.LightGray, Color.Black, "", new Vector2(150, 150), "", 40));
        Controlls.put("RUButton", new UI_ButtonControll(new Vector2(240, 575), Color.LightGray, Color.Black, "", new Vector2(150, 150), "", 40));

        Controlls.get("ENButton").onTap[0] = new Delegate() {
            @Override
            public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
                TapHandler(0);
            }
        };
        Controlls.get("UAButton").onTap[0] = new Delegate() {
            @Override
            public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
                TapHandler(1);
            }
        };
        Controlls.get("RUButton").onTap[0] = new Delegate() {
            @Override
            public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
                TapHandler(2);
            }
        };

        ((UI_ButtonControll) Controlls.get("ENButton")).IcoDrawEvent = new Delegate() {
            @Override
            public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
                DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get(DataControll.I.Flags.get(0)), position.sub(size.div(2)), size);
            }
        };

        ((UI_ButtonControll) Controlls.get("UAButton")).IcoDrawEvent = new Delegate() {
            @Override
            public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
                DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get(DataControll.I.Flags.get(1)), position.sub(size.div(2)), size);
            }
        };
        ((UI_ButtonControll) Controlls.get("RUButton")).IcoDrawEvent = new Delegate() {
            @Override
            public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
                DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get(DataControll.I.Flags.get(2)), position.sub(size.div(2)), size);
            }
        };
    }

    public void TapHandler(int i) {
        if (DataControll.LanguageIndex == -1 || SaveLoadService.I.isFirstPlay()) {
            SaveLoadService.I.setAsFirstPlayReady();
            DataControll.LanguageIndex = i;
            SpriteAdapter.SelfPointer.Language = SpriteAdapter.SelfPointer.AvailableLanguages.get(DataControll.LanguageIndex);

            DataControll.GameModeIndex = 0;

            InstructionsContoll.I.createInstruction(EngineService.I.Pages);

            ((MainMenu) EngineService.I.Pages.get("MainMenu")).InstructionIndex = 0;
            ((ProfilePage) EngineService.I.Pages.get("ProfilePage")).InstructionIndex = 0;
            ((SelectGameModePage) EngineService.I.Pages.get("SelectGameModePage")).InstructionIndex = 0;

            ((MainMenu) EngineService.I.Pages.get("MainMenu")).Instructions = InstructionsContoll.I.MainMenuInstructionsPart1;
            ((ProfilePage) EngineService.I.Pages.get("ProfilePage")).Instructions = InstructionsContoll.I.SetupsPagePart1;
            ((SelectGameModePage) EngineService.I.Pages.get("SelectGameModePage")).Instructions = InstructionsContoll.I.SelectGamePart1;
        } else {
            DataControll.LanguageIndex = i;
            SpriteAdapter.SelfPointer.Language = SpriteAdapter.SelfPointer.AvailableLanguages.get(DataControll.LanguageIndex);
        }

        SaveLoadService.I.SaveSetups();
        EngineService.I.NavigateTo("MainMenu", "", false);
    }

    @Override
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

    @Override
    public void OnNavigatetTo(Object data) {
        super.OnNavigatetTo(data);
    }

    @Override
    public Boolean Update(float timeDelta) {
        return super.Update(timeDelta);
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch) {
        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, DataControll.WindowHeight), Color.Beige, 1, 0, 1);

        //бекграунд и полоска
        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, 100), Color.LightGray, 1, 0, 2);
        spriteBatch.DrawText("LanguageLine1", new Vector2(DataControll.WindowWidth / 2, 55), Color.Black, 1, "Arial32", 0.7f, TextAlign.CenterXY, 0, 30);

        //Advertisement place holder
        spriteBatch.FillRectangle(new Vector2(0, 720), new Vector2(DataControll.WindowWidth, 80), Color.LightGray, 1, 0, 20);

        super.Draw(spriteBatch);
    }
}
