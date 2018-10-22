package com.koma.filforever.RootFolder.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder.InstructionArrow;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder.InstructionBase;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder.InstructionTextRectange;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;

public class InstructionsContoll {
    public static InstructionsContoll I = new InstructionsContoll();

    public List<InstructionBase> MainMenuInstructionsPart1;
    public List<InstructionBase> MainMenuInstructionsPart2;

    public List<InstructionBase> SetupsPagePart1;

    public List<InstructionBase> SelectGamePart1;

    public void createInstruction(Map<String, IGameState> Pages) {
        MainMenuInstructionsPart1 = new LinkedList<InstructionBase>();
        MainMenuInstructionsPart2 = new LinkedList<InstructionBase>();
        SetupsPagePart1 = new LinkedList<InstructionBase>();
        SelectGamePart1 = new LinkedList<InstructionBase>();

        InstructionBase instruction;

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 200), Color.White, SpriteAdapter.SelfPointer.getString("MainMenuLine1"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 235), Color.White, SpriteAdapter.SelfPointer.getString("MainMenuLine2"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 675), Color.White, SpriteAdapter.SelfPointer.getString("TapAnywhereToContinue"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        MainMenuInstructionsPart1.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 200), Color.White, SpriteAdapter.SelfPointer.getString("MainMenuLine4"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 675), Color.White, SpriteAdapter.SelfPointer.getString("TapAnywhereToContinue"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionArrow(Color.White, new Vector2(240, 300), new Vector2(350, 555)));
        instruction.AddHiControll(Pages.get("MainMenu").Controlls.get("LanguageButton"));
        MainMenuInstructionsPart1.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 200), Color.White, SpriteAdapter.SelfPointer.getString("MainMenuLine5"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 260), Color.White, SpriteAdapter.SelfPointer.getString("MainMenuLine6"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 675), Color.White, SpriteAdapter.SelfPointer.getString("TapAnywhereToContinue"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionArrow(Color.White, new Vector2(240, 300), new Vector2(130, 555)));
        instruction.AddHiControll(Pages.get("MainMenu").Controlls.get("SoundButton"));
        MainMenuInstructionsPart1.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 200), Color.White, SpriteAdapter.SelfPointer.getString("MainMenuLine7"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionArrow(Color.White, new Vector2(240, 300), new Vector2(240, 540)));
        instruction.AddFitControll(Pages.get("MainMenu").Controlls.get("NameChangeButton"));
        MainMenuInstructionsPart1.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 150), Color.White, SpriteAdapter.SelfPointer.getString("MainMenuLine8"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionArrow(Color.White, new Vector2(20, 275), new Vector2(60, 275)));
        instruction.AddInstruction(new InstructionArrow(Color.White, new Vector2(460, 275), new Vector2(420, 275)));
        instruction.AddFitControll(Pages.get("MainMenu").Controlls.get("StartGameButton"));
        MainMenuInstructionsPart2.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 300), Color.White, SpriteAdapter.SelfPointer.getString("MainMenuLine9"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionArrow(Color.White, new Vector2(350, 200), new Vector2(160, 200)));
        instruction.AddFitControll(Pages.get("ProfilePage").Controlls.get("NameChangeButton"));
        SetupsPagePart1.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 200), Color.White, SpriteAdapter.SelfPointer.getString("MainMenuLine10"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionArrow(Color.White, new Vector2(300, 60), new Vector2(100, 60)));
        instruction.AddFitControll(Pages.get("ProfilePage").Controlls.get("MenuButton"));
        SetupsPagePart1.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 200), Color.White, SpriteAdapter.SelfPointer.getString("SelecModText"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 300), Color.White, SpriteAdapter.SelfPointer.getString("LinModRules"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 630), Color.White, SpriteAdapter.SelfPointer.getString("ToNextModLine"), 300, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionArrow(Color.White, new Vector2(400, 400), new Vector2(400, 490)));
        instruction.AddFitControll(Pages.get("SelectGameModePage").Controlls.get("RightArrowButton"));
        SelectGamePart1.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 200), Color.White, SpriteAdapter.SelfPointer.getString("SquaModRuls"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 630), Color.White, SpriteAdapter.SelfPointer.getString("ToNextModLine"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionArrow(Color.White, new Vector2(400, 400), new Vector2(400, 490)));
        instruction.AddFitControll(Pages.get("SelectGameModePage").Controlls.get("RightArrowButton"));
        SelectGamePart1.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 200), Color.White, SpriteAdapter.SelfPointer.getString("SnakModRuls"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 630), Color.White, SpriteAdapter.SelfPointer.getString("TapAnywhereToContinue"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        SelectGamePart1.add(instruction);

        instruction = new InstructionBase(SpriteAdapter.SelfPointer.GetWindowSize(), 0.86f, Color.Black, 80);
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 200), Color.White, SpriteAdapter.SelfPointer.getString("MultiplyHintText"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        instruction.AddInstruction(new InstructionTextRectange(new Vector2(240, 630), Color.White, SpriteAdapter.SelfPointer.getString("LastLineText"), 350, TextAlign.CenterXY, "Arial20", 0.8f));
        SelectGamePart1.add(instruction);
    }
}
