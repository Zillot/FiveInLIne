package com.koma.filforever.RootFolder.GamePages;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.SpriteEffects;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.InstructionsContoll;
import com.koma.filforever.RootFolder.Data.SaveLoadService;

public class LoadingScreen extends IGameState {
    //отщет до навигации к главному меню
    public FloatController toNavigate;
    public String text;

    public LoadingScreen() {
        toNavigate = new FloatController(2.6f);
        toNavigate.GoUp();
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
        text = "Loading";

        if (((String) data).compareTo("RepeatTutorial") == 0 || SaveLoadService.I.isFirstPlay()) {
            SaveLoadService.I.setAsFirstPlayReady();
            DataControll.LanguageIndex = -1;

            DataControll.GameModeIndex = 0;

            InstructionsContoll.I.createInstruction(EngineService.I.Pages);

            ((MainMenu) EngineService.I.Pages.get("MainMenu")).InstructionIndex = 0;
            ((ProfilePage) EngineService.I.Pages.get("ProfilePage")).InstructionIndex = 0;
            ((SelectGameModePage) EngineService.I.Pages.get("SelectGameModePage")).InstructionIndex = 0;

            ((MainMenu) EngineService.I.Pages.get("MainMenu")).Instructions = InstructionsContoll.I.MainMenuInstructionsPart1;
            ((ProfilePage) EngineService.I.Pages.get("ProfilePage")).Instructions = InstructionsContoll.I.SetupsPagePart1;
            ((SelectGameModePage) EngineService.I.Pages.get("SelectGameModePage")).Instructions = InstructionsContoll.I.SelectGamePart1;
        }
        super.OnNavigatetTo(data);
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = super.Update(timeDelta);

        toNavigate.Update(timeDelta);

        //навигация к главному меню
        if (toNavigate.GetRegularValue() == 1) {
            toRet = true;

            toNavigate.Value = 0;
            toNavigate.GoUp();
            text += ".";
        }

        if (text.compareTo("Loading...") == 0) {
            toRet = true;

            if (DataControll.LanguageIndex == -1) {
                EngineService.I.NavigateTo("LanguageSelectPage", "", false);
            } else {
                EngineService.I.NavigateTo("MainMenu", "", false);
            }
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch) {
        spriteBatch.Clear(Color.Beige);
        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, 800), Color.Beige, 1, 0, 35);

        spriteBatch.DrawTexture("KomaLogo", new Vector2(240, 240), null, new Vector2(220), 0.9f, Color.White, 1, 0, SpriteEffects.None, 40);

        spriteBatch.DrawText(text, new Vector2(240, 600), Color.Black, 1, "Arial20", 1, TextAlign.CenterXY, 0, 42);

        super.Draw(spriteBatch);
    }
}
